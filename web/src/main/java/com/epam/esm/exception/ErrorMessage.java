package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private Integer errorCode;
    private String errorMessage;
}
