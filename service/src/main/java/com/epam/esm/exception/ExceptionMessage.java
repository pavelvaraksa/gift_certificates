package com.epam.esm.exception;

import org.springframework.stereotype.Component;

@Component
public class ExceptionMessage {
    public static final String CERTIFICATE_NAME_NOT_FILLED = "certificate.name.not.filled";
    public static final String CERTIFICATE_NAME_INCORRECT = "certificate.name.incorrect";
    public static final String DESCRIPTION_NOT_FILLED = "description.not.filled";
    public static final String DESCRIPTION_INCORRECT = "description.incorrect";
    public static final String PRICE_NOT_FILLED = "price.not.filled";
    public static final String PRICE_INCORRECT= "price.incorrect";
    public static final String DURATION_NOT_FILLED = "duration.not.filled";
    public static final String DURATION_INCORRECT = "duration.incorrect";
    public static final String CERTIFICATE_NOT_FOUND = "certificate.not.found";

    public static final String TAG_NAME_NOT_FILLED = "tag.name.not.filled";
    public static final String TAG_INCORRECT = "tag.name.incorrect";
    public static final String TAG_NOT_FOUND = "tag.not.found";
}
