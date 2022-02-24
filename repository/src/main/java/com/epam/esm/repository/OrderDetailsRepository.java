package com.epam.esm.repository;

import com.epam.esm.domain.OrderDetails;

public interface OrderDetailsRepository {
    /**
     * Save object
     *
     * @param orderDetails - create object
     * @return - created object
     */
    OrderDetails save(OrderDetails orderDetails);

    /**
     * Find object by id.
     *
     * @param key - object id.
     * @return - optional of found object.
     */
    OrderDetails findById(Long key);
}
