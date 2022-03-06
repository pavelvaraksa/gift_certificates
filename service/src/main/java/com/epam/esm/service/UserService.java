package com.epam.esm.service;

import com.epam.esm.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * User service layer
 * Works with user repository layer
 */
public interface UserService {
    /**
     * Find all users
     *
     * @return - page of users or empty page
     */
    Page<User> findAll(Pageable pageable, boolean isDeleted);

    /**
     * Find a user by id
     *
     * @param id - user id
     * @return - optional of found user
     */
    Optional<User> findById(Long id);

    /**
     * Find a user by login
     *
     * @param login - user login
     * @return - optional of found user
     */
    Optional<User> findByLogin(String login);

    /**
     * Create a user
     *
     * @param user - create a user
     * @return - created user
     */
    User save(User user);

    /**
     * Update a user.
     *
     * @param id   - user id
     * @param user - updated user
     * @return - operation result (user updated full or partly)
     */
    User updateById(Long id, User user);

    /**
     * Delete a user
     *
     * @param id - user id
     */
    void deleteById(Long id);
}
