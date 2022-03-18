package com.epam.esm.service;

import com.epam.esm.domain.Order;
import com.epam.esm.util.ColumnOrderName;
import com.epam.esm.util.SortType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Order service layer
 * Works with order repository layer
 */
public interface OrderService {
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
     * Find an order by id
     *
     * @param id - order id
     * @return - optional of found order
     */
    Optional<Order> findById(Long id);

    /**
     * Create an order
     *
     * @param user            - user
     * @param giftCertificate - list of certificates
     * @return - created order
     */
    Order save(Long user, List<Long> giftCertificate);

    /**
     * Activate an order
     *
     * @param id        - order id
     * @param isCommand - command for activate
     * @return - activated order
     */
    Order activateById(Long id, boolean isCommand);

    /**
     * Delete an order
     *
     * @param id - order id
     * @return - order
     */
    Order deleteById(Long id);
}
