package com.epam.esm.validator;

import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceValidException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exception.MessageException.USER_FIRSTNAME_INCORRECT;
import static com.epam.esm.exception.MessageException.USER_FIRSTNAME_NOT_FILLED;
import static com.epam.esm.exception.MessageException.USER_LASTNAME_INCORRECT;
import static com.epam.esm.exception.MessageException.USER_LASTNAME_NOT_FILLED;
import static com.epam.esm.exception.MessageException.USER_LOGIN_INCORRECT;
import static com.epam.esm.exception.MessageException.USER_LOGIN_NOT_FILLED;
import static com.epam.esm.exception.MessageException.USER_PASSWORD_INCORRECT;
import static com.epam.esm.exception.MessageException.USER_PASSWORD_NOT_FILLED;

@Log4j2
@Component
public class UserValidator {
    private static final String REGEXP_VALUE = "[\\p{L}\\p{N}_-]{1,30}";

    private static boolean isMatcherValid(String regexp, String stringFromUi) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(stringFromUi);
        return matcher.matches();
    }

    public static boolean isUserValid(User user) {
        String login = user.getLogin();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String password = user.getPassword();

        return isLoginValid(login) && isUserFirstNameValid(firstName) && isUserLastNameValid(lastName) && isUserPasswordValid(password);
    }

    private static boolean isLoginValid(String login) {
        if (login == null) {
            log.error("Login was not filled");
            throw new ServiceValidException(USER_LOGIN_NOT_FILLED);
        } else if (!isMatcherValid(REGEXP_VALUE, login)) {
            log.error("Entered user login was not correct.Use words,numbers,underscore and hyphen");
            throw new ServiceValidException(USER_LOGIN_INCORRECT);
        }

        return true;
    }

    private static boolean isUserFirstNameValid(String firstName) {
        if (firstName == null) {
            log.error("Firstname was not filled");
            throw new ServiceValidException(USER_FIRSTNAME_NOT_FILLED);
        } else if (!isMatcherValid(REGEXP_VALUE, firstName)) {
            log.error("Entered user firstname was not correct.Use words,numbers,underscore and hyphen");
            throw new ServiceValidException(USER_FIRSTNAME_INCORRECT);
        }

        return true;
    }

    private static boolean isUserLastNameValid(String lastName) {
        if (lastName == null) {
            log.error("Lastname was not filled");
            throw new ServiceValidException(USER_LASTNAME_NOT_FILLED);
        } else if (!isMatcherValid(REGEXP_VALUE, lastName)) {
            log.error("Entered user lastname was not correct.Use words,numbers,underscore and hyphen");
            throw new ServiceValidException(USER_LASTNAME_INCORRECT);
        }

        return true;
    }

    private static boolean isUserPasswordValid(String password) {
        if (password == null) {
            log.error("Password was not filled");
            throw new ServiceValidException(USER_PASSWORD_NOT_FILLED);
        } else if (!isMatcherValid(REGEXP_VALUE, password)) {
            log.error("Entered user password was not correct.Use words,numbers,underscore and hyphen");
            throw new ServiceValidException(USER_PASSWORD_INCORRECT);
        }

        return true;
    }
}

