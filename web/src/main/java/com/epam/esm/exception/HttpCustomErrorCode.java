package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HttpCustomErrorCode {
    ITEM_VALID_EXCEPTION(40401),

    ITEM_INCORRECT_SEARCH_EXCEPTION(40402),

    ITEM_DUPLICATE_NAME_EXCEPTION(40403),

    ITEM_NOT_FOUND_EXCEPTION(40404);

    @JsonValue
    private final Integer httpCustomErrorCode;

    HttpCustomErrorCode(Integer httpCustomErrorCode) {
        this.httpCustomErrorCode = httpCustomErrorCode;
    }
}
