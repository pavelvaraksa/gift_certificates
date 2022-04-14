package com.epam.esm.security.exception;

public class CustomJwtException extends RuntimeException {
    public CustomJwtException(String message) {
        super(message);
    }
}
