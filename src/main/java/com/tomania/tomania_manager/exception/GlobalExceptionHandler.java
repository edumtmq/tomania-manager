package com.tomania.tomania_manager.exception;

import com.tomania.tomania_manager.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> tratarArgumentoInvalido(MethodArgumentNotValidException exception) {
        String mensagem = exception
                .getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        ErrorResponseDTO erro = new ErrorResponseDTO(
                        mensagem,
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.name(),
                        LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> tratarDadosInvalidos(HttpMessageNotReadableException exception) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                "Dados enviados em formato inválido",
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> tratarParametroInvalido(MethodArgumentTypeMismatchException exception) {
        String mensagem = "Valor inválido para o parâmetro " + exception.getName();
        ErrorResponseDTO erro = new ErrorResponseDTO(
                mensagem,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> tratarErroInterno(Exception exception) {
        ErrorResponseDTO erro = new ErrorResponseDTO(
                "Ocorreu um erro interno no servidor",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

}
