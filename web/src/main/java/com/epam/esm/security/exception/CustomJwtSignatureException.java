package com.epam.esm.security.exception;

public class CustomJwtSignatureException extends RuntimeException {
    public CustomJwtSignatureException(String message) {
        super(message);
    }
}
