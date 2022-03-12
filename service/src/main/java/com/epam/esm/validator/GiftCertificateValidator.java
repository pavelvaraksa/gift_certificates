package com.epam.esm.validator;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.ServiceValidException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.esm.exception.MessageException.CERTIFICATE_NAME_NOT_FILLED;
import static com.epam.esm.exception.MessageException.CERTIFICATE_NAME_INCORRECT;
import static com.epam.esm.exception.MessageException.DESCRIPTION_NOT_FILLED;
import static com.epam.esm.exception.MessageException.DESCRIPTION_INCORRECT;
import static com.epam.esm.exception.MessageException.PRICE_INCORRECT;
import static com.epam.esm.exception.MessageException.PRICE_NOT_FILLED;
import static com.epam.esm.exception.MessageException.DURATION_INCORRECT;
import static com.epam.esm.exception.MessageException.DURATION_NOT_FILLED;

@Log4j2
@Component
public class GiftCertificateValidator {
    private static final String REGEXP_NAME = "[\\p{L}\\p{N}_-]{1,30}";
    private static final String REGEXP_DESCRIPTION = "[\\p{L}\\p{N}_-]{1,50}";
    private static final String REGEXP_PRICE = "[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
    private static final String REGEXP_DURATION = "[+]?[1-9]\\d{0,5}";

    private static boolean isMatcherValid(String regexp, String stringFromUi) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(stringFromUi);

        return matcher.matches();
    }

    public static boolean isGiftCertificateValid(GiftCertificate giftCertificate) {
        String name = giftCertificate.getName();
        String description = giftCertificate.getDescription();
        BigDecimal price = giftCertificate.getPrice();
        Integer duration = giftCertificate.getDuration();

        return isGiftCertificateNameValid(name)
                && isDescriptionValid(description)
                && isPriceValid(String.valueOf(price))
                && isDurationValid(String.valueOf(duration));
    }

    private static boolean isGiftCertificateNameValid(String name) {

        if (name == null) {
            log.error("Gift certificate name was not filled");
            throw new ServiceValidException(CERTIFICATE_NAME_NOT_FILLED);
        } else if (!isMatcherValid(REGEXP_NAME, name)) {
            log.error("Entered name was not correct.Use words,numbers,underscore and hyphen.Max size is 30 symbols");
            throw new ServiceValidException(CERTIFICATE_NAME_INCORRECT);
        }

        return true;
    }

    private static boolean isDescriptionValid(String description) {

        if (description == null) {
            log.error("Gift certificate description was not filled");
            throw new ServiceValidException(DESCRIPTION_NOT_FILLED);
        } else if (!isMatcherValid(REGEXP_DESCRIPTION, description)) {
            log.error("Entered description was not correct.Use words,numbers,underscore and hyphen.Max size is 50 symbols");
            throw new ServiceValidException(DESCRIPTION_INCORRECT);
        }

        return true;
    }

    private static boolean isPriceValid(String price) {

        if (price.equals("null")) {
            log.error("Gift certificate price was not filled");
            throw new ServiceValidException(PRICE_NOT_FILLED);
        } else if (!isMatcherValid(REGEXP_PRICE, price)) {
            log.error("Entered gift certificate price was not correct.Use fractional positive number");
            throw new ServiceValidException(PRICE_INCORRECT);
        }

        return true;
    }

    private static boolean isDurationValid(String duration) {

        if (duration.equals("null")) {
            log.error("Gift certificate duration was not filled");
            throw new ServiceValidException(DURATION_NOT_FILLED);
        } else if (!isMatcherValid(REGEXP_DURATION, duration)) {
            log.error("Entered gift certificate duration was not correct.Use integer positive number");
            throw new ServiceValidException(DURATION_INCORRECT);
        }

        return true;
    }
}