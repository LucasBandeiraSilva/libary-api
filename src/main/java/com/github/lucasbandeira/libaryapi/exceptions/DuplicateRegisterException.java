package com.github.lucasbandeira.libaryapi.exceptions;

public class DuplicateRegisterException extends RuntimeException {
    public DuplicateRegisterException( String message ) {
        super(message);
    }
}
