package com.epam.esm.exception;

public class ServiceExistException extends RuntimeException {
    public ServiceExistException() {
    }

    public ServiceExistException(String message) {
        super(message);
    }

    public ServiceExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceExistException(Throwable cause) {
        super(cause);
    }
}
