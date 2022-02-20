package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class DefaultExceptionHandler {
    private final ResourceBundleMessageSource messageSource;
    private final String INCORRECT_SEARCH = "Incorrect input in search field";
    private final String INCORRECT_SYNTAX = "Incorrect input in body field";
    private final String NOT_ALLOWED = "Method not allowed this function";

    @ExceptionHandler(ServiceValidException.class)
    public ResponseEntity<FrameException> handleValidException(ServiceValidException ex, Locale locale) {
        return createResponseEntity(ex, locale, ErrorCode.ITEM_VALID_EXCEPTION, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<FrameException> handleNotFoundException(ServiceNotFoundException ex, Locale locale) {
        return createResponseEntity(ex, locale, ErrorCode.ITEM_NOT_FOUND_EXCEPTION, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceExistException.class)
    public ResponseEntity<FrameException> handleDuplicateNameException(ServiceExistException ex, Locale locale) {
        return createResponseEntity(ex, locale, ErrorCode.ITEM_DUPLICATE_NAME_EXCEPTION, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectSearchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(new ErrorMessage(400, INCORRECT_SEARCH), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectSearchException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(new ErrorMessage(400, INCORRECT_SEARCH), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectSyntaxException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new ErrorMessage(400, INCORRECT_SYNTAX), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleNotSupportException(HttpRequestMethodNotSupportedException ex) {

        return new ResponseEntity<>(new ErrorMessage(405, NOT_ALLOWED), HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<FrameException> createResponseEntity(RuntimeException runtimeException, Locale locale, ErrorCode errorCode, HttpStatus httpStatus) {
        String exceptionMessage = messageSource.getMessage(runtimeException.getMessage(), null, locale);
        FrameError frameError = new FrameError(errorCode, exceptionMessage);
        FrameException frameException = new FrameException(httpStatus, frameError);
        return new ResponseEntity<>(frameException, httpStatus);
    }
}


