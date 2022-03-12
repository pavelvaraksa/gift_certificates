package com.epam.esm.exception;

public class ServiceExistException extends RuntimeException {
    public ServiceExistException(String message) {
        super(message);
    }
}
