package com.epam.esm.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log4j2
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ServiceValidException.class)
    public ResponseEntity<ErrorMessage> handleValidException(ServiceValidException ex) {

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectInputException(MethodArgumentTypeMismatchException ex) {

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
