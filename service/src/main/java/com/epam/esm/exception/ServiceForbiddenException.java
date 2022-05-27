package com.epam.esm.exception;

public class ServiceForbiddenException extends RuntimeException {
    public ServiceForbiddenException(String message) {
        super(message);
    }
}
