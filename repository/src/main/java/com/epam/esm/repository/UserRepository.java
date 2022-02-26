package com.epam.esm.repository;

import com.epam.esm.domain.User;

import java.util.Optional;

/**
 * User repository interface layer
 * Works with database
 */
public interface UserRepository extends CrdRepository<Long, User> {
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
     * @param user - user
     * @return - updated user
     */
    User updateById(User user);
}
