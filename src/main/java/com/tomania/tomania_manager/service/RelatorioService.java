package com.tomania.tomania_manager.service;

import com.tomania.tomania_manager.dto.RelatorioProdutoMovimentacaoDTO;
import com.tomania.tomania_manager.entity.Movimentacao;
import com.tomania.tomania_manager.entity.Produto;
import com.tomania.tomania_manager.enums.MotivoMovimentacao;
import com.tomania.tomania_manager.enums.TipoMovimentacao;
import com.tomania.tomania_manager.exception.ProdutoNaoEncontradoException;
import com.tomania.tomania_manager.repository.MovimentacaoRepository;
import com.tomania.tomania_manager.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public RelatorioService(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public List<RelatorioProdutoMovimentacaoDTO> relatorioProdutoMovimentacao(Integer produtoId, Integer dias) {
        Integer periodoDias = 0;
        if(dias == null){
            periodoDias = 7;
        }
        else if(dias > 0){
            periodoDias = dias;
        } else {
            throw new RuntimeException("Dias invalido");
        }

        LocalDateTime fim = LocalDateTime.now();
        LocalDateTime inicio = fim.minusDays(periodoDias);

        List<Produto> produtos;
        if(produtoId != null){
            Produto produto = produtoRepository.findById(produtoId).orElseThrow(() ->
                    new ProdutoNaoEncontradoException("Produto não encontrado"));
            produtos = List.of(produto);
        } else {
            produtos = produtoRepository.findByAtivoTrue();
        }

        List<RelatorioProdutoMovimentacaoDTO> relatorio = new ArrayList<>();

        for (Produto produto : produtos) {

            List<Movimentacao> movimentacoes = movimentacaoRepository
                    .findByProduto_IdAndDataMovimentacaoBetween(
                            produto.getId(),
                            inicio,
                            fim
                    );

            Integer entradaCompra = movimentacoes.stream()
                    .filter(movimentacao -> movimentacao.getTipo() == TipoMovimentacao.ENTRADA)
                    .filter(movimentacao -> movimentacao.getMotivo() == MotivoMovimentacao.COMPRA)
                    .mapToInt(Movimentacao::getQuantidade)
                    .sum();


            Integer entradaBonificacao = movimentacoes.stream()
                    .filter(movimentacao -> movimentacao.getTipo() == TipoMovimentacao.ENTRADA)
                    .filter(movimentacao -> movimentacao.getMotivo() == MotivoMovimentacao.BONIFICACAO)
                    .mapToInt(Movimentacao::getQuantidade)
                    .sum();

            Integer entradaAjuste = movimentacoes.stream()
                    .filter(movimentacao -> movimentacao.getTipo() == TipoMovimentacao.ENTRADA)
                    .filter(movimentacao -> movimentacao.getMotivo() == MotivoMovimentacao.AJUSTE)
                    .mapToInt(Movimentacao::getQuantidade)
                    .sum();

            Integer saidaProducao = movimentacoes.stream()
                    .filter(movimentacao -> movimentacao.getTipo() == TipoMovimentacao.SAIDA)
                    .filter(movimentacao -> movimentacao.getMotivo() == MotivoMovimentacao.PRODUCAO)
                    .mapToInt(Movimentacao::getQuantidade)
                    .sum();

            Integer saidaPerda = movimentacoes.stream()
                    .filter(movimentacao -> movimentacao.getTipo() == TipoMovimentacao.SAIDA)
                    .filter(movimentacao -> movimentacao.getMotivo() == MotivoMovimentacao.PERDA)
                    .mapToInt(Movimentacao::getQuantidade)
                    .sum();

            Integer saidaValidade = movimentacoes.stream()
                    .filter(movimentacao -> movimentacao.getTipo() == TipoMovimentacao.SAIDA)
                    .filter(movimentacao -> movimentacao.getMotivo() == MotivoMovimentacao.VALIDADE)
                    .mapToInt(Movimentacao::getQuantidade)
                    .sum();

            Integer saidaAjuste= movimentacoes.stream()
                    .filter(movimentacao -> movimentacao.getTipo() == TipoMovimentacao.SAIDA)
                    .filter(movimentacao -> movimentacao.getMotivo() == MotivoMovimentacao.AJUSTE)
                    .mapToInt(Movimentacao::getQuantidade)
                    .sum();

            Integer totalEntradas = entradaCompra + entradaBonificacao + entradaAjuste;

            Integer totalSaidas = saidaProducao + saidaPerda + saidaValidade + saidaAjuste;

            Double consumoMedioDiario = saidaProducao.doubleValue() / periodoDias;

            RelatorioProdutoMovimentacaoDTO linha = new RelatorioProdutoMovimentacaoDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getEstoqueAtual(),
                    periodoDias,
                    entradaCompra,
                    entradaBonificacao,
                    entradaAjuste,
                    saidaProducao,
                    saidaPerda,
                    saidaValidade,
                    saidaAjuste,
                    totalEntradas,
                    totalSaidas,
                    consumoMedioDiario
            );

            relatorio.add(linha);
        }

        return relatorio;
    }
}
