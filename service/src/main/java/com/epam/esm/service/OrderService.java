package com.epam.esm.service;

import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

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
     * Find user by order id
     *
     * @param id - order id
     * @return - user or empty
     */
    Optional<User> findUserByOrderId(Long id);
}
