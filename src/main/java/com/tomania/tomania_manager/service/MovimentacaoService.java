package com.tomania.tomania_manager.service;

import com.tomania.tomania_manager.dto.EntradaLoteRequestDTO;
import com.tomania.tomania_manager.dto.MovimentacaoRequestDTO;
import com.tomania.tomania_manager.dto.MovimentacaoResponseDTO;
import com.tomania.tomania_manager.dto.ProdutoResponseDTO;
import com.tomania.tomania_manager.entity.Movimentacao;
import com.tomania.tomania_manager.entity.Produto;
import com.tomania.tomania_manager.enums.MotivoMovimentacao;
import com.tomania.tomania_manager.enums.TipoMovimentacao;
import com.tomania.tomania_manager.exception.EstoqueInsuficienteException;
import com.tomania.tomania_manager.exception.MotivoMovimentacaoInvalidoException;
import com.tomania.tomania_manager.exception.MovimentacaoNaoEncontradaException;
import com.tomania.tomania_manager.exception.ProdutoNaoEncontradoException;
import com.tomania.tomania_manager.mapper.MovimentacaoMapper;
import com.tomania.tomania_manager.repository.MovimentacaoRepository;
import com.tomania.tomania_manager.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoMapper movimentacaoMapper;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, ProdutoRepository produtoRepository, MovimentacaoMapper movimentacaoMapper) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.movimentacaoMapper = movimentacaoMapper;
        this.produtoRepository = produtoRepository;
    }


    @Transactional
    public MovimentacaoResponseDTO registrarMovimentacao(MovimentacaoRequestDTO movimentacaoRequestDTO) {
        validarMotivoPorTipo(movimentacaoRequestDTO);

        Produto produto = produtoRepository.findById(movimentacaoRequestDTO.produtoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        if (movimentacaoRequestDTO.tipo() == TipoMovimentacao.ENTRADA) {
            produto.setEstoqueAtual(produto.getEstoqueAtual() + movimentacaoRequestDTO.quantidade());
        }

        else if (movimentacaoRequestDTO.tipo() == TipoMovimentacao.SAIDA) {
            if (produto.getEstoqueAtual() < movimentacaoRequestDTO.quantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente");
            }

            produto.setEstoqueAtual(produto.getEstoqueAtual() - movimentacaoRequestDTO.quantidade());
        }

        Movimentacao movimentacao = movimentacaoMapper.toMovimentacao(movimentacaoRequestDTO, produto);

        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);

        return movimentacaoMapper.toMovimentacaoResponse(movimentacaoSalva);
    }

//    -> Lista movimentações
    public List<MovimentacaoResponseDTO> listarMovimentacoes() {
        return movimentacaoRepository.findAll()
                .stream()
                .map(movimentacaoMapper::toMovimentacaoResponse)
                .toList();
    }

//    -> buscar movimentações por id
    public MovimentacaoResponseDTO buscarMovimentacaoPorId(Integer id) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElseThrow(() ->
                new MovimentacaoNaoEncontradaException("Movimentação não encontrada"));

        return movimentacaoMapper.toMovimentacaoResponse(movimentacao);
    }


    public List<MovimentacaoResponseDTO> listarMovimentacoesPorProduto(Integer produtoId) {
        return movimentacaoRepository.findByProduto_Id(produtoId)
                .stream()
                .map(movimentacaoMapper::toMovimentacaoResponse)
                .toList();
    }

    private void validarMotivoPorTipo(MovimentacaoRequestDTO dto) {
        if (dto.tipo() == TipoMovimentacao.ENTRADA) {
            if (
                    dto.motivo() != MotivoMovimentacao.COMPRA &&
                            dto.motivo() != MotivoMovimentacao.AJUSTE &&
                            dto.motivo() != MotivoMovimentacao.BONIFICACAO
            ) {
                throw new MotivoMovimentacaoInvalidoException("Motivo inválido para movimentação de entrada");
            }
        }
        if (dto.tipo() == TipoMovimentacao.SAIDA) {
            if (
                    dto.motivo() != MotivoMovimentacao.PRODUCAO &&
                            dto.motivo() != MotivoMovimentacao.PERDA &&
                            dto.motivo() != MotivoMovimentacao.AJUSTE &&
                            dto.motivo() != MotivoMovimentacao.VALIDADE
            ) {
                throw new MotivoMovimentacaoInvalidoException("Motivo inválido para movimentação de saida");
            }
        }
    }

    @Transactional
    public List<MovimentacaoResponseDTO> regitrarEntradaEmLote(List<EntradaLoteRequestDTO> entradas){
        return entradas.stream()
                .map(entrada -> {
                            MovimentacaoRequestDTO movimentacaoRequestDTO = new MovimentacaoRequestDTO(
                                    entrada.produtoId(),
                                    TipoMovimentacao.ENTRADA,
                                    MotivoMovimentacao.COMPRA,
                                    entrada.quantidade(),
                                    entrada.responsavel()
                            );
                            return registrarMovimentacao(movimentacaoRequestDTO);
                    }
                )
                .toList();
    }

    public List<MovimentacaoResponseDTO> listarPorTipo(TipoMovimentacao tipo) {
        return movimentacaoRepository.findByTipo(tipo)
                .stream()
                .map(movimentacaoMapper::toMovimentacaoResponse)
                .toList();
    }

    public List<MovimentacaoResponseDTO> listarPorPeriodo(
            LocalDateTime inicio,
            LocalDateTime fim) {
        return movimentacaoRepository.findByDataMovimentacaoBetween(inicio, fim)
                .stream()
                .map(movimentacaoMapper::toMovimentacaoResponse)
                .toList();
    }

    public List<MovimentacaoResponseDTO> filtroCombinado( Integer produtoId,
                                                          TipoMovimentacao tipo,
                                                          LocalDateTime inicio,
                                                          LocalDateTime fim) {

        if (inicio == null || fim == null) {
            LocalDate hoje = LocalDate.now();
            inicio = hoje.atStartOfDay();
            fim = hoje.atTime(23, 59, 59);
        } else if (inicio != null && fim == null) {
            fim = LocalDateTime.now();
        } else if (inicio == null && fim != null) {
            throw new RuntimeException("Informe a data inicial");
        } else if (inicio.isAfter(fim)) {
            throw new RuntimeException("A data inicial não pode ser maior que a data final");
        }

        if (produtoId != null) {
            produtoRepository.findById(produtoId).orElseThrow(() ->
                    new ProdutoNaoEncontradoException("Produto não encontrado"));
        }

        List<Movimentacao> movimentacoes;

        if (produtoId != null && tipo != null) {
            movimentacoes = movimentacaoRepository.findByProduto_IdAndTipoAndDataMovimentacaoBetween
                    (produtoId, tipo, inicio, fim);
        }
        else if (produtoId != null) {
            movimentacoes = movimentacaoRepository.findByProduto_IdAndDataMovimentacaoBetween
                    (produtoId, inicio, fim);
        }
        else if (tipo != null) {
            movimentacoes = movimentacaoRepository.findByTipoAndDataMovimentacaoBetween
                    (tipo, inicio, fim);
        }
        else {
            movimentacoes = movimentacaoRepository.findByDataMovimentacaoBetween
                    (inicio, fim);
        }

        return movimentacoes.stream()
                .map(movimentacaoMapper::toMovimentacaoResponse)
                .toList();
    }
}
