package com.epam.esm.repository;

import com.epam.esm.domain.Order;

import java.util.List;

/**
 * Order repository interface layer
 * Works with database
 */
public interface OrderRepository extends CrdRepository<Long, Order> {
    /**
     * Find all orders by user id
     *
     * @param id - user id
     * @return - list of orders
     */
    List<Order> findAllOrdersByUserId(Long id);

    /**
     * Find exist order by id
     *
     * @param id - order id
     * @return - optional of found order
     */
    Order findByExistId(Long id);
}


