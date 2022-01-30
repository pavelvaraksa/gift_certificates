package com.epam.esm.exception;

public class ServiceValidException extends RuntimeException {
    public ServiceValidException() {
    }

    public ServiceValidException(String message) {
        super(message);
    }

    public ServiceValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceValidException(Throwable cause) {
        super(cause);
    }
}
