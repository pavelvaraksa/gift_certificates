package com.epam.esm.repository;

import com.epam.esm.domain.Order;
import com.epam.esm.util.ColumnOrderName;
import com.epam.esm.util.SortType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Order repository interface layer
 * Works with database
 */
public interface OrderRepository extends CrdRepository<Long, Order> {
    /**
     * Find orders with pagination, sorting and info about deleted orders
     *
     * @param pageable  - pagination config
     * @param column    - order column
     * @param sort      - sort type
     * @param isDeleted - info about deleted orders
     * @return - list of orders or empty list
     */
    List<Order> findAll(Pageable pageable, Set<ColumnOrderName> column, SortType sort, boolean isDeleted);

    /**
     * Find exist order by id
     *
     * @param id - order id
     * @return - optional of found order
     */
    Order findByExistId(Long id);

    /**
     * Activate gift order by id
     *
     * @param id - order id
     * @return - activated order
     */
    Order activateById(Long id);
}


