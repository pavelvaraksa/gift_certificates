package com.epam.esm.exception;

public class ServiceNotAuthorized extends RuntimeException {
    public ServiceNotAuthorized(String message) {
        super(message);
    }
}
