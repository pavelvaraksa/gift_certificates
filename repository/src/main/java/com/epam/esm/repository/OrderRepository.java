package com.epam.esm.repository;

import com.epam.esm.domain.Order;

/**
 * Order repository interface layer
 * Works with database
 */
public interface OrderRepository extends CrdRepository<Long, Order> {
    /**
     * Find order by id
     *
     * @param id - order id
     * @return - optional of found order
     */
    Order findByExistId(Long id);
}
