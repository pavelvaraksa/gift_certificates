package com.epam.esm.repository;

import com.epam.esm.domain.Order;

/**
 * Order repository interface layer.
 * Works with database.
 */
public interface OrderRepository extends CrdRepository<Long, Order> {
}
