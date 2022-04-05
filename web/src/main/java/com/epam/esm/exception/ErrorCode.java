package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    ITEM_VALID_EXCEPTION(40001),
    ITEM_NOT_AUTHORIZED_EXCEPTION(40102),
    ITEM_FORBIDDEN_EXCEPTION(40303),
    ITEM_NOT_FOUND_EXCEPTION(40404),
    ITEM_DUPLICATE_NAME_EXCEPTION(40905);

    @JsonValue
    private final Integer httpCustomErrorCode;

    ErrorCode(Integer httpCustomErrorCode) {
        this.httpCustomErrorCode = httpCustomErrorCode;
    }
}
