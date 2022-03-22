package com.epam.esm.service;

import com.epam.esm.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * User service layer
 * Works with user repository layer
 */
public interface UserService extends CrdService<User, Long> {
    /**
     * Find all users
     *
     * @return - list of users or empty list
     */
    List<User> findAll();

    /**
     * Find user by id
     *
     * @param id - user id
     * @return - optional of found user
     */
    Optional<User> findById(Long id);

    /**
     * Find user by login
     *
     * @param login - user login
     * @return - optional of found user
     */
    Optional<User> findByLogin(String login);

    /**
     * Create user
     *
     * @param user - create user
     * @return - created user
     */
    User save(User user);

    /**
     * Update user
     *
     * @param id   - user id
     * @param user - updated user
     * @return - operation result (user updated full or partly)
     */
    User updateById(Long id, User user);

    /**
     * Activate user
     *
     * @param id        - user id
     * @param isCommand - command for activate
     * @return - activated user
     */
    User activateById(Long id, boolean isCommand);

    /**
     * Delete user
     *
     * @param id - user id
     * @return - deleted user
     */
    User deleteById(Long id);
}
