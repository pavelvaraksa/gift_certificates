package com.epam.esm.service;

import com.epam.esm.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * User service layer
 * Works with user repository layer
 */
public interface UserService extends CreateService<User>, ReadDeleteService<User, Long> {
    /**
     * Find all users
     *
     * @return - list of users or empty list
     */
    List<User> findAll();

    /**
     * Find user by login
     *
     * @param login - user login
     * @return - optional of found user
     */
    Optional<User> findByLogin(String login);

    /**
     * Update user
     *
     * @param id   - user id
     * @param user - updated user
     * @return - operation result (user updated full or partly)
     */
    User updateById(Long id, User user);
}
