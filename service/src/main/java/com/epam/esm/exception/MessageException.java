package com.epam.esm.exception;

import org.springframework.stereotype.Component;

@Component
public class MessageException {
    public static final String CERTIFICATE_NAME_NOT_FILLED = "certificate.name.not.filled";
    public static final String CERTIFICATE_NAME_INCORRECT = "certificate.name.incorrect";
    public static final String DESCRIPTION_NOT_FILLED = "description.not.filled";
    public static final String DESCRIPTION_INCORRECT = "description.incorrect";
    public static final String PRICE_NOT_FILLED = "price.not.filled";
    public static final String PRICE_INCORRECT= "price.incorrect";
    public static final String DURATION_NOT_FILLED = "duration.not.filled";
    public static final String DURATION_INCORRECT = "duration.incorrect";
    public static final String CERTIFICATE_NOT_FOUND = "certificate.not.found";
    public static final String CERTIFICATE_EXIST = "certificate.exist";
    public static final String TAG_NAME_NOT_FILLED = "tag.name.not.filled";
    public static final String TAG_INCORRECT = "tag.name.incorrect";
    public static final String TAG_NOT_FOUND = "tag.not.found";
    public static final String TAG_EXIST= "tag.exist";
    public static final String USER_LOGIN_NOT_FILLED = "user.login.not.filled";
    public static final String USER_LOGIN_INCORRECT = "user.login.incorrect";
    public static final String USER_FIRSTNAME_NOT_FILLED = "user.firstname.not.filled";
    public static final String USER_FIRSTNAME_INCORRECT = "user.firstname.incorrect";
    public static final String USER_LASTNAME_NOT_FILLED = "user.lastname.not.filled";
    public static final String USER_LASTNAME_INCORRECT = "user.lastname.incorrect";
    public static final String USER_NOT_FOUND = "user.not.found";
    public static final String USER_EXIST = "user.exist";
}
