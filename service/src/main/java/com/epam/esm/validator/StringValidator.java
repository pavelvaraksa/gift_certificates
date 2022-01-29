package com.epam.esm.validator;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ServiceValidException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Component
public class StringValidator {
    private static final String REGEXP_NAME = "[a-zA-Z[0-9]]{1,30}";
    private static final String REGEXP_DESCRIPTION = "[a-zA-Z[0-9]]{1,50}";
    private static final String REGEXP_PRICE = "^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$";
    private static final String REGEXP_DURATION = "[0-9]{1,5}";

    private static boolean isMatcherValid(String regexp, String stringFromUi) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(stringFromUi);

        return matcher.matches();
    }

    public static void isGiftCertificateValid(GiftCertificateDto giftCertificateDto) {
        String name = giftCertificateDto.getName();
        String description = giftCertificateDto.getDescription();
        Double price = giftCertificateDto.getPrice();
        Integer duration = giftCertificateDto.getDuration();

        boolean result = !(isGiftCertificateNameValid(name) && isDescriptionValid(description) && isPriceValid(String.valueOf(price)) && isDurationValid(String.valueOf(duration)));
    }

    public static boolean isTagValid(Tag tag) {
        String name = tag.getName();

        return !isTagNameValid(name);
    }

    private static boolean isGiftCertificateNameValid(String name) {

        if (name != null && isMatcherValid(REGEXP_NAME, name)) {
            log.info("Entered gift certificate name was filled");
            return true;
        }

        String errorMessage = "Entered gift certificate name was not filled";
        log.error(errorMessage);
        throw new ServiceValidException(errorMessage);
    }

    private static boolean isDescriptionValid(String description) {

        if (description != null && isMatcherValid(REGEXP_DESCRIPTION, description)) {
            log.info("Entered description was filled");
            return true;
        }

        String errorMessage = "Entered description was not filled";
        log.error(errorMessage);
        throw new ServiceValidException(errorMessage);
    }

    private static boolean isPriceValid(String price) {

        if (price != null && isMatcherValid(REGEXP_PRICE, price)) {
            log.info("Entered price was filled");
            return true;
        }

        String errorMessage = "Entered price was not filled";
        log.error(errorMessage);
        throw new ServiceValidException(errorMessage);
    }

    private static boolean isDurationValid(String duration) {

        if (duration != null && isMatcherValid(REGEXP_DURATION, duration)) {
            log.info("Entered duration was filled");
            return true;
        }

        String errorMessage = "Entered duration was not filled";
        log.error(errorMessage);
        throw new ServiceValidException(errorMessage);
    }

    private static boolean isTagNameValid(String name) {

        if (name != null && isMatcherValid(REGEXP_NAME, name)) {
            log.info("Entered tag name was filled");
            return true;
        }

        String errorMessage = "Entered tag name was not filled";
        log.error(errorMessage);
        throw new ServiceValidException(errorMessage);
    }
}

