package com.epam.esm.service;

import com.epam.esm.domain.Order;

import java.util.List;

/**
 * Order service layer
 * Works with order repository layer
 */
public interface OrderService extends ReadDeleteService<Order, Long> {
    /**
     * Find all orders
     *
     * @return - list of orders or empty list
     */
    List<Order> findAll();

    /**
     * Create order
     *
     * @param user            - user
     * @param giftCertificate - list of certificates
     * @return - created order
     */
    Order save(Long user, List<Long> giftCertificate);

    /**
     * Activate order
     *
     * @param id        - order id
     * @param isCommand - command for activate
     * @return - activated order
     */
    Order activateById(Long id, boolean isCommand);
}
