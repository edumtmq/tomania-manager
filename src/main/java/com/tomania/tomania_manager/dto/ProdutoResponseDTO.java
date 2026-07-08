package com.tomania.tomania_manager.dto;

import com.tomania.tomania_manager.enums.StatusProduto;

public record ProdutoResponseDTO (
        Integer id,
        String nome,
        boolean ativo,
        Integer estoqueMinimo,
        Integer estoqueAtual,
        StatusProduto status
){

}
