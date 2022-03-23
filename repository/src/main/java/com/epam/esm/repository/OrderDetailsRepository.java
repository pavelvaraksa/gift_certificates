package com.epam.esm.repository;

import com.epam.esm.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Order details repository layer
 * Works with database
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
