package com.epam.esm.util;

import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class SqlQuery {
    private static final char PERCENT_SYMBOL = '%';
    private static final char SPACE_SYMBOL = ' ';
    private static final String COMMA_SYMBOL = ",";
    private static final String FIND_ALL_SORT_QUERY = "select * from gift_certificate order by";

    public static String findAllSorted(Set<ColumnName> orderBy, SortType sortType) {
        String orderSortLine = orderBy.stream()
                .map(columnName -> new StringJoiner(String.valueOf(SPACE_SYMBOL))
                        .add(columnName.name())
                        .add(sortType.name()).toString())
                .collect(Collectors.joining(COMMA_SYMBOL));

        return FIND_ALL_SORT_QUERY + SPACE_SYMBOL + orderSortLine;
    }

    public static String accessQueryForPercent(String sqlQuery) {
        return PERCENT_SYMBOL + sqlQuery + PERCENT_SYMBOL;
    }
}
