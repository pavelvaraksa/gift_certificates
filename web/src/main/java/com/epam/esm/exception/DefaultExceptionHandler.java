package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;

import static com.epam.esm.exception.HttpCustomErrorCode.*;

@Log4j2
@ControllerAdvice
@AllArgsConstructor
public class DefaultExceptionHandler {
    private final ResourceBundleMessageSource messageSource;

    @ExceptionHandler(ServiceValidException.class)
    public ResponseEntity<FrameException> handleValidException(ServiceValidException ex, Locale locale) {

        log.error(ex.getMessage(), ex);
        return createResponseEntity(ex, locale, ITEM_VALID_EXCEPTION, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<FrameException> handleNotFoundException(ServiceNotFoundException ex, Locale locale) {

        log.error(ex.getMessage(), ex);
        return createResponseEntity(ex, locale, ITEM_NOT_FOUND_EXCEPTION, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<FrameException> handleIncorrectSearchException(MethodArgumentTypeMismatchException ex, Locale locale) {

        log.error(ex.getMessage(), ex);
        return createResponseEntity(ex, locale, ITEM_INCORRECT_SEARCH_EXCEPTION, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<FrameException> handleDuplicateNameException(DuplicateKeyException ex, Locale locale) {

        log.error(ex.getMessage(), ex);
        return createResponseEntity(ex, locale, ITEM_DUPLICATE_NAME_EXCEPTION, HttpStatus.CONFLICT);
    }

    private ResponseEntity<FrameException> createResponseEntity(RuntimeException runtimeException, Locale locale, HttpCustomErrorCode httpCustomErrorCode, HttpStatus httpStatus) {
        String exceptionMessage = messageSource.getMessage(runtimeException.getMessage(), null, locale);
        FrameError frameError = new FrameError(httpCustomErrorCode, exceptionMessage);
        FrameException frameException = new FrameException(httpStatus, frameError);
        return new ResponseEntity<>(frameException, httpStatus);
    }
}

