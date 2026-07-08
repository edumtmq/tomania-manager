package com.tomania.tomania_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ErrorResponseDTO (
        String mensagem,
        Integer status,
        String erro,
        LocalDateTime timestamp
) {
}
