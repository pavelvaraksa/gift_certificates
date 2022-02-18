package com.epam.esm.validator;

import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceValidException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exception.MessageException.TAG_INCORRECT;
import static com.epam.esm.exception.MessageException.TAG_NAME_NOT_FILLED;

@Log4j2
@Component
public class TagValidator {
    private static final String REGEXP_NAME = "[\\p{L}\\p{N}_-]{1,30}";

    private static boolean isMatcherValid(String regexp, String stringFromUi) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(stringFromUi);
        return matcher.matches();
    }

    public static boolean isTagValid(Tag tag) {
        String name = tag.getName();
        return isTagNameValid(name);
    }

    private static boolean isTagNameValid(String name) {
        if (name == null) {
            log.error("Tag name was not filled");
            throw new ServiceValidException(TAG_NAME_NOT_FILLED);
        } else if (!isMatcherValid(REGEXP_NAME, name)) {
            log.error("Entered tag name was not correct.Use words,numbers,underscore and hyphen");
            throw new ServiceValidException(TAG_INCORRECT);
        }

        return true;
    }
}


