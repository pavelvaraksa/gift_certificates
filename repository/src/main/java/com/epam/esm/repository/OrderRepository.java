package com.epam.esm.repository;

import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

/**
 * Order repository layer
 * Works with database
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find orders by user id
     *
     * @param id - user id
     * @return - list of orders or empty list
     */
    @Query("select ord from Order ord join User user on ord.user.id = user.id where user.id = ?1")
    Set<Order> findAllByUserId(Long id);

    /**
     * Find user by order id
     *
     * @param id - order id
     * @return - user or empty
     */
    @Query("select ord.user from Order ord where ord.id = ?1")
    Optional<User> findUserByOrderId(Long id);

    /**
     * Delete order by id
     *
     * @param id - order id
     */
    @Modifying
    @Query("delete from Order ord where ord.id = ?1")
    void deleteOrderById(Long id);
}


