package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    ITEM_VALID_EXCEPTION(40001),

    ITEM_NOT_FOUND_EXCEPTION(40403),

    ITEM_DUPLICATE_NAME_EXCEPTION(40902);

    @JsonValue
    private final Integer httpCustomErrorCode;

    ErrorCode(Integer httpCustomErrorCode) {
        this.httpCustomErrorCode = httpCustomErrorCode;
    }
}
