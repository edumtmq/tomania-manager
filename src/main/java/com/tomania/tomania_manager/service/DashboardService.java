package com.tomania.tomania_manager.service;

import com.tomania.tomania_manager.dto.DashboardResumoDTO;
import com.tomania.tomania_manager.entity.Produto;
import com.tomania.tomania_manager.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProdutoRepository produtoRepository;

    public DashboardResumoDTO gerarResumo() {

        List<Produto> produtosAtivos = produtoRepository.findByAtivoTrue();

        long totalProdutosAtivos = produtosAtivos.size();

        long produtosOk = produtosAtivos.stream()
                .filter(produto -> produto.getEstoqueAtual() > produto.getEstoqueMinimo())
                .count();

        long produtosAtencao = produtosAtivos.stream()
                .filter(produto -> produto.getEstoqueAtual().equals(produto.getEstoqueMinimo()))
                .count();

        long produtosComprar = produtosAtivos.stream()
                .filter(produto -> produto.getEstoqueAtual() < produto.getEstoqueMinimo())
                .count();

        return new DashboardResumoDTO(
                totalProdutosAtivos,
                produtosOk,
                produtosAtencao,
                produtosComprar
        );
    }

}
