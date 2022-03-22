package com.epam.esm.repository;

import com.epam.esm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * User repository layer
 * Works with database
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by login
     *
     * @param login - user login
     * @return - optional of found user
     */
    Optional<User> findByLogin(String login);

    /**
     * Update user by id
     *
     * @param user - user
     * @return - updated user
     */
    @Modifying
    @Query("update User user set user.login = ?1, user.firstName = ?2, user.lastName = ?3 where user.id = ?4")
    User updateById(User user);

    /**
     * Activate user by id
     *
     * @param id - user id
     * @return - activated user
     */
    @Modifying
    @Query("update User user set user.isActive = false where user.id = ?1")
    User activateById(Long id);
}
