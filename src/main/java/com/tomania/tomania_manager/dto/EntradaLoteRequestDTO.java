package com.tomania.tomania_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EntradaLoteRequestDTO(
        @NotNull
        Integer produtoId,

        @NotNull
        @Positive
        Integer quantidade,

        @NotBlank
        String responsavel
) {
}
