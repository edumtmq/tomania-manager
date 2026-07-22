package com.tomania.tomania_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EntradaLoteRequestDTO(
        @NotNull(message = "O produto é obrigatório")
        Integer produtoId,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantidade,

        @NotBlank(message = "O responsável é obrigatório")
        String responsavel
) {
}
