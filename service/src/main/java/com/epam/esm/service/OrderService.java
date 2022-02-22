package com.epam.esm.service;

import com.epam.esm.domain.Order;

import java.util.List;
import java.util.Optional;

/**
 * Order service layer.
 * Works with order repository layer.
 */
public interface OrderService {
    /**
     * Find all orders.
     *
     * @return - list of orders or empty list.
     */
    List<Order> findAll();

    /**
     * Find an order by id.
     *
     * @param id - order id.
     * @return - optional of found order.
     */
    Optional<Order> findById(Long id);

    /**
     * Create an order.
     *
     * @return - created order.
     */
    Order save(Long userId, List<Long> giftCertificateId);

    /**
     * Delete an order.
     *
     * @param id - order id.
     */
    void deleteById(Long id);
}
