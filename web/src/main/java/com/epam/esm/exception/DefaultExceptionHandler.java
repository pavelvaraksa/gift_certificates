package com.epam.esm.exception;

import lombok.extern.log4j.Log4j2;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log4j2
@ControllerAdvice
public class DefaultExceptionHandler {
    private static final String INCORRECT_SEARCH_DATA = "Search format data was not correct";
    private static final String INCORRECT_FILLED_DATA = "Filled data was not correct";
    private static final String DUPLICATE_NAME = "This item name already exist";
    private static final String SQL_ERROR = "Bad SQL grammar exception";

    @ExceptionHandler(ServiceValidException.class)
    public ResponseEntity<ErrorMessage> handleValidException(ServiceValidException ex) {

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleDuplicateNameException(HttpMessageNotReadableException ex) {

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage(400, INCORRECT_FILLED_DATA), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectDataException(MethodArgumentTypeMismatchException ex) {

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage(400, INCORRECT_SEARCH_DATA), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(ServiceNotFoundException ex) {

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage(404, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorMessage> handleDuplicateNameException(DuplicateKeyException ex) {

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage(409, DUPLICATE_NAME), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorMessage> handleSQLException(PSQLException ex) {

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage(500, SQL_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


