package com.tomania.tomania_manager.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
