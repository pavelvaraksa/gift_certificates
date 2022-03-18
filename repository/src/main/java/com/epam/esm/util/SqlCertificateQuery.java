package com.epam.esm.util;

import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class SqlCertificateQuery {
    private static final char SPACE_SYMBOL = ' ';
    private static final String COMMA_SYMBOL = ",";
    private static final String FIND_ALL_SORT_QUERY = "select certificate from GiftCertificate certificate order by";

    public static String findAllSorted(Set<ColumnCertificateName> orderBy, SortType sortType) {
        String orderSortLine = orderBy.stream()
                .map(columnName -> new StringJoiner(String.valueOf(SPACE_SYMBOL))
                        .add(columnName.name())
                        .add(sortType.name()).toString())
                .collect(Collectors.joining(COMMA_SYMBOL));
        return FIND_ALL_SORT_QUERY + SPACE_SYMBOL + orderSortLine;
    }
}
