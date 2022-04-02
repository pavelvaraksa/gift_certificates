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
     * Find user password by login
     *
     * @param login - user login
     * @return - user password
     */
    @Query("select u.password from User u where u.login = ?1")
    String findUserPasswordByLogin(String login);

    /**
     * Update user by id
     *
     * @param login     - user login
     * @param firstName - user firstname
     * @param lastName  - user lastname
     * @param id        - user id
     */
    @Modifying
    @Query("update User user set user.login = ?1, user.firstName = ?2, user.lastName = ?3 where user.id = ?4")
    void updateById(String login, String firstName, String lastName, Long id);

    /**
     * t
     * Activate user by id
     *
     * @param id - user id
     */
    @Modifying
    @Query("update User user set user.isActive = false where user.id = ?1")
    void activateById(Long id);
}
