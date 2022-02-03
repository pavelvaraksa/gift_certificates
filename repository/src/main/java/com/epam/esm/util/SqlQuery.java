package com.epam.esm.util;

public class SqlQuery {
    private static final char PERCENT_VALUE = '%';

    public static String accessQuery(String sqlQuery) {
        return PERCENT_VALUE + sqlQuery + PERCENT_VALUE;
    }
}
