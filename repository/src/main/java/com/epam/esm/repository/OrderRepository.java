package com.epam.esm.repository;

import com.epam.esm.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Order repository layer
 * Works with database
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find exist order by id
     *
     * @param id - order id
     * @return - order
     */
    @Query("select order from Order order where order.id = ?1")
    Order findByExistId(Long id);

    /**
     * Activate gift order by id
     *
     * @param id - order id
     * @return - activated order
     */
    @Modifying
    @Query("update Order order set order.isActive = false where order.id = ?1")
    Order activateById(Long id);
}


