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
     * Find order id with the highest cost by user id
     *
     * @param id - user id
     * @return - order id
     */
    Long findIdWithHighestCost(List<Long> id);

    /**
     * Find an order by id
     *
     * @param id - order id
     * @return - optional of found order
     */
    Optional<Order> findById(Long id);

    /**
     * Create an order
     * @param userId - user id
     * @param giftCertificateId - list of certificates id
     *
     * @return - created order
     */
    Order save(Long userId, List<Long> giftCertificateId);

    /**
     * Delete an order
     *
     * @param id - order id
     */
    void deleteById(Long id);
}
