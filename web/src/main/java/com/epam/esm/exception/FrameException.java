package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class FrameException {
    private HttpStatus httpStatus;
    private FrameError frameError;
}
