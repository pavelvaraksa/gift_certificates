package com.epam.esm.repository;

import com.epam.esm.domain.OrderDetails;

/**
 * Order details repository interface layer
 * Works with database
 */
public interface OrderDetailsRepository {
    /**
     * Save order details
     *
     * @param orderDetails - create order details
     * @return - created order details
     */
    OrderDetails save(OrderDetails orderDetails);

    /**
     * Find order details by id
     *
     * @param id - order details id
     * @return - optional of found order details
     */
    OrderDetails findById(Long id);
}
