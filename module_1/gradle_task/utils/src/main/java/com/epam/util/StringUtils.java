package com.epam.util;

import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

/**
 * Class {@code StringUtil} designed for implementing
 * method {@code isPositiveNumber}
 *
 * @author Pavel Varaksa
 */
public class StringUtils {
    public static boolean isPositiveNumber(String value) {

        if (!isCreatable(value)) {
            throw new NumberFormatException("Incorrect value: " + value);
        }

        return Double.parseDouble(value) > 0;
    }
}
