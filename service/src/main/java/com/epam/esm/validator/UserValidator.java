package com.epam.esm.validator;

import com.epam.esm.domain.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        return isLoginValid(login) && isUserFirstNameValid(firstName) && isUserLastNameValid(lastName);
    }

    private static boolean isLoginValid(String login) {
        boolean isResult = true;

        if (login == null) {
            log.error("Login was not filled");
            return false;
        } else if (!isMatcherValid(REGEXP_VALUE, login)) {
            log.error("Entered user login was not correct.Use words,numbers,underscore and hyphen");
            return false;
        }

        return isResult;
    }

    private static boolean isUserFirstNameValid(String firstName) {
        boolean isResult = true;

        if (firstName == null) {
            log.error("Firstname was not filled");
            isResult = false;
        } else if (!isMatcherValid(REGEXP_VALUE, firstName)) {
            log.error("Entered user firstname was not correct.Use words,numbers,underscore and hyphen");
            isResult = false;
        }

        return isResult;
    }

    private static boolean isUserLastNameValid(String lastName) {
        boolean isResult = true;

        if (lastName == null) {
            log.error("Lastname was not filled");
            isResult = false;
        } else if (!isMatcherValid(REGEXP_VALUE, lastName)) {
            log.error("Entered user lastname was not correct.Use words,numbers,underscore and hyphen");
            isResult = false;
        }

        return isResult;
    }
}

