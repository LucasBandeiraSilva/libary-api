package com.github.lucasbandeira.libaryapi.exceprions;

public class DuplicateRegisterException extends RuntimeException {
    public DuplicateRegisterException( String message ) {
        super(message);
    }
}
