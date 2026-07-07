package com.tomania.tomania_manager.dto;

import com.tomania.tomania_manager.enums.MotivoMovimentacao;
import com.tomania.tomania_manager.enums.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentacaoResponseDTO (
        Integer id,
        Integer produtoId,
        String produtoNome,
        TipoMovimentacao tipo,
        MotivoMovimentacao motivo,
        Integer quantidade,
        LocalDateTime dataMovimentacao,
        String responsavel
){
}
