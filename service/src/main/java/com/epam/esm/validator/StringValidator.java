package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceValidException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exception.ExceptionMessage.*;

@Log4j2
@Component
public class StringValidator {
    private static final String REGEXP_NAME = "[\\p{L}\\p{N}_-]{1,30}";
    private static final String REGEXP_DESCRIPTION = "[\\p{L}\\p{N}_-]{1,50}";
    private static final String REGEXP_PRICE = "[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
    private static final String REGEXP_DURATION = "[+]?[1-9]\\d{0,5}";

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

        boolean result = !(isGiftCertificateNameValid(name)
                && isDescriptionValid(description)
                && isPriceValid(String.valueOf(price))
                && isDurationValid(String.valueOf(duration)));
    }

    public static void isTagValid(TagDto tagDto) {
        String name = tagDto.getName();

        boolean result = isTagNameValid(name);
    }

    private static boolean isGiftCertificateNameValid(String name) {

        if (name == null) {
            String errorMessage = CERTIFICATE_NAME_NOT_FILLED;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        } else if (!isMatcherValid(REGEXP_NAME, name)) {
            String errorMessage = CERTIFICATE_NAME_INCORRECT;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        }

        return true;
    }

    private static boolean isDescriptionValid(String description) {

        if (description == null) {
            String errorMessage = DESCRIPTION_NOT_FILLED;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        } else if (!isMatcherValid(REGEXP_DESCRIPTION, description)) {
            String errorMessage = DESCRIPTION_INCORRECT;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        }

        return true;
    }

    private static boolean isPriceValid(String price) {

        if (price.equals("null")) {
            String errorMessage = PRICE_NOT_FILLED;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        } else if (!isMatcherValid(REGEXP_PRICE, price)) {
            String errorMessage = PRICE_INCORRECT;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        }

        return true;
    }

    private static boolean isDurationValid(String duration) {

        if (duration.equals("null")) {
            String errorMessage = DURATION_NOT_FILLED;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        } else if (!isMatcherValid(REGEXP_DURATION, duration)) {
            String errorMessage = DURATION_INCORRECT;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        }

        return true;
    }

    private static boolean isTagNameValid(String name) {

        if (name == null) {
            String errorMessage = TAG_NAME_NOT_FILLED;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        } else if (isMatcherValid(REGEXP_NAME, name)) {
            String errorMessage = TAG_INCORRECT;
            log.error(errorMessage);
            throw new ServiceValidException(errorMessage);
        }

        return true;
    }
}

