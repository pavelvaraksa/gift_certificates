package com.epam.esm.repository;

import com.epam.esm.domain.User;
import com.epam.esm.util.ColumnUserName;
import com.epam.esm.util.SortType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * User repository interface layer
 * Works with database
 */
public interface UserRepository extends CrdRepository<Long, User> {
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
     * Find all users id
     *
     * @return - list of users id
     */
    List<Long> findAllForWidelyUsedTag();

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

    /**
     * Activate user by id
     *
     * @param id - user id
     * @return - activated user
     */
    User activateById(Long id);
}
