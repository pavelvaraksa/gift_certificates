package com.epam.esm.service;

import com.epam.esm.domain.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * Order service layer
 * Works with order repository layer
 */
public interface OrderService {
    /**
     * Find all orders
     *
     * @return - page of orders or empty page
     */
    Page<Order> findAll(Pageable pageable, boolean isDeleted);

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
