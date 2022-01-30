package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FrameError {
    private HttpCustomErrorCode httpCustomErrorCode;
    private String errorMessage;
}
