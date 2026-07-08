package com.tomania.tomania_manager.dto;

public record DashboardResumoDTO(
        Long totalProdutosAtivos,
        Long produtosOk,
        Long produtosAtencao,
        Long produtosComprar
){
}
