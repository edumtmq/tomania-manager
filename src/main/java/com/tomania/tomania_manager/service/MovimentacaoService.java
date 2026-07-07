package com.tomania.tomania_manager.service;

import com.tomania.tomania_manager.dto.MovimentacaoRequestDTO;
import com.tomania.tomania_manager.dto.MovimentacaoResponseDTO;
import com.tomania.tomania_manager.entity.Movimentacao;
import com.tomania.tomania_manager.entity.Produto;
import com.tomania.tomania_manager.enums.TipoMovimentacao;
import com.tomania.tomania_manager.mapper.MovimentacaoMapper;
import com.tomania.tomania_manager.repository.MovimentacaoRepository;
import com.tomania.tomania_manager.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Produto produto = produtoRepository.findById(movimentacaoRequestDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (movimentacaoRequestDTO.tipo() == TipoMovimentacao.ENTRADA) {
            produto.setEstoqueAtual(produto.getEstoqueAtual() + movimentacaoRequestDTO.quantidade());
        }

        else if (movimentacaoRequestDTO.tipo() == TipoMovimentacao.SAIDA) {
            if (produto.getEstoqueAtual() < movimentacaoRequestDTO.quantidade()) {
                throw new RuntimeException("Estoque insuficiente");
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

}
