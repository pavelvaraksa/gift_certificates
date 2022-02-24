package com.epam.esm.repository;

import com.epam.esm.domain.Order;

/**
 * Order repository interface layer.
 * Works with database.
 */
public interface OrderRepository extends CrdRepository<Long, Order> {
    /**
     * Find object by id
     *
     * @param id - object id
     * @return - optional of found object
     */
    Order findByExistId(Long id);
}
