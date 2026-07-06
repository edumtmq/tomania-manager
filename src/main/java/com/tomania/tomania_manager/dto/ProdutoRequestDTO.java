package com.tomania.tomania_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProdutoRequestDTO (
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @Positive(message = "A quantidade minima tem que ser positiva")
        Integer estoqueMinimo
) {

}
