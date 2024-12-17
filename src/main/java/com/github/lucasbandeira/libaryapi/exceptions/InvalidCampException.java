package com.github.lucasbandeira.libaryapi.exceptions;

import lombok.Getter;

public class InvalidCampException extends RuntimeException {

    @Getter
    private String field;

    public InvalidCampException( String field, String message ) {
        super(message);
        this.field = field;
    }
}
