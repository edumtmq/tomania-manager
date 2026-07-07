package com.tomania.tomania_manager.dto;

import com.tomania.tomania_manager.enums.MotivoMovimentacao;
import com.tomania.tomania_manager.enums.TipoMovimentacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimentacaoRequestDTO (

        @NotNull(message = "O produto é obrigatório")
        Integer produtoId,

        @NotNull(message = "O tipo da movimentação é obrigatório")
        TipoMovimentacao tipo,

        @NotNull(message = "O motivo da movimentação é obrigatório")
        MotivoMovimentacao motivo,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantidade,

        @NotBlank(message = "O responsável é obrigatório")
        String responsavel


){
}
