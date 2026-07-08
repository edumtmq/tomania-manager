package com.tomania.tomania_manager.exception;

public class EstoqueInsuficienteException extends RuntimeException{

    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
