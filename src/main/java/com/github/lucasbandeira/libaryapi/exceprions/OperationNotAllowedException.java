package com.github.lucasbandeira.libaryapi.exceprions;

public class OperationNotAllowedException extends RuntimeException {
    public OperationNotAllowedException( String message ) {
        super(message);
    }
}
