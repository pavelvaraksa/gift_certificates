package com.epam.esm.service;

import com.epam.esm.domain.User;
import com.epam.esm.util.ColumnUserName;
import com.epam.esm.util.SortType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * User service layer
 * Works with user repository layer
 */
public interface UserService {
    /**
     * Find users with pagination, sorting and info about deleted users
     *
     * @param pageable  - pagination config
     * @param column    - user column
     * @param sort      - sort type
     * @param isDeleted - info about deleted users
     * @return - list of users or empty list
     */
    List<User> findAll(Pageable pageable, Set<ColumnUserName> column, SortType sort, boolean isDeleted);

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
     * Activate a user
     *
     * @param id        - user id
     * @param isCommand - command for activate
     * @return - activated user
     */
    User activateById(Long id, boolean isCommand);

    /**
     * Delete a user
     *
     * @param id - user id
     * @return - deleted user
     */
    User deleteById(Long id);
}
