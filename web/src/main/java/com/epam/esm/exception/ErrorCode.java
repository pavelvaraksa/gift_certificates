package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    ITEM_VALID_EXCEPTION(40401),

    ITEM_DUPLICATE_NAME_EXCEPTION(40402),

    ITEM_NOT_FOUND_EXCEPTION(40403);

    @JsonValue
    private final Integer httpCustomErrorCode;

    ErrorCode(Integer httpCustomErrorCode) {
        this.httpCustomErrorCode = httpCustomErrorCode;
    }
}
