package com.epam.util;

import java.util.Arrays;

/**
 * Class {@code Utils} designed for implementing
 * method {@code isAllPositiveNumbers}
 *
 * @author Pavel Varaksa
 */
public class Utils {
    public static boolean isAllPositiveNumbers(String... values) {
        return Arrays.stream(values).allMatch(StringUtils::isPositiveNumber);
    }
}



