package com.tomania.tomania_manager.exception;

import com.tomania.tomania_manager.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> tratarProdutoNaoEncontradoException(ProdutoNaoEncontradoException exception) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(MovimentacaoNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> tratarMovimentacaoNaoEncontradaException(MovimentacaoNaoEncontradaException exception) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ErrorResponseDTO> tratarEstoqueInsuficienteException(EstoqueInsuficienteException exception) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MotivoMovimentacaoInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> tratarMotivoMovimentacaoInvalidoException(MotivoMovimentacaoInvalidoException exception) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
