package com.epam.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class {@code StringUtilTest} designed for testing methods
 *
 * @author Pavel Varaksa
 */
class StringUtilsTest {
    private static final String INTEGER_POSITIVE_NUMBER = "10";
    private static final String INTEGER_NEGATIVE_NUMBER = "-2";
    private static final String FRACTIONAL_POSITIVE_NUMBER = "3.51";
    private static final String FRACTIONAL_NEGATIVE_NUMBER = "-23.52";
    private static final String INCORRECT_FRACTIONAL_NUMBER = "21,63";
    private static final String ZERO_NUMBER = "0";
    private static final String SYMBOL_VALUE = "java";
    private static final String SPACE_VALUE = " ";
    private static final String NULL_VALUE = null;
    private static final String INCORRECT_INPUT_FORMAT_DATA = "....";

    @Test
    public void testIntegerPositiveNumber() {
        Assertions.assertTrue(StringUtils.isPositiveNumber(INTEGER_POSITIVE_NUMBER));
    }

    @Test
    public void testIntegerNegativeNumber() {
        Assertions.assertFalse(StringUtils.isPositiveNumber(INTEGER_NEGATIVE_NUMBER));
    }

    @Test
    public void testFractionalPositiveNumber() {
        Assertions.assertTrue(StringUtils.isPositiveNumber(FRACTIONAL_POSITIVE_NUMBER));
    }

    @Test
    public void testFractionalNegativeNumber() {
        Assertions.assertFalse(StringUtils.isPositiveNumber(FRACTIONAL_NEGATIVE_NUMBER));
    }

    @Test
    public void testIncorrectFractionalNumber() {
        assertThrows(NumberFormatException.class,
                () -> StringUtils.isPositiveNumber(INCORRECT_FRACTIONAL_NUMBER));
    }

    @Test
    public void testZeroNumber() {
        Assertions.assertFalse(StringUtils.isPositiveNumber(ZERO_NUMBER));
    }

    @Test
    public void testSymbolValue() {
        assertThrows(NumberFormatException.class,
                () -> StringUtils.isPositiveNumber(SYMBOL_VALUE));
    }

    @Test
    public void testSpaceValue() {
        assertThrows(NumberFormatException.class,
                () -> StringUtils.isPositiveNumber(SPACE_VALUE));
    }

    @Test
    public void testNullValue() {
        assertThrows(NumberFormatException.class,
                () -> StringUtils.isPositiveNumber(NULL_VALUE));
    }

    @Test
    public void testIncorrectInputFormatData() {
        assertThrows(NumberFormatException.class,
                () -> StringUtils.isPositiveNumber(INCORRECT_INPUT_FORMAT_DATA));
    }
}