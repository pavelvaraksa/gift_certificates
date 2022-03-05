package com.epam.esm.exception;

import java.util.function.Supplier;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String message) {
        super(message);
    }
}
