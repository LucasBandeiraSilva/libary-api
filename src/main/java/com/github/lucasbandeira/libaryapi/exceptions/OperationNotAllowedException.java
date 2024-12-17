package com.github.lucasbandeira.libaryapi.exceptions;

public class OperationNotAllowedException extends RuntimeException {
    public OperationNotAllowedException( String message ) {
        super(message);
    }
}
