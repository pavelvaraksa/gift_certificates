package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FrameError {
    private ErrorCode errorCode;
    private String errorMessage;
}
