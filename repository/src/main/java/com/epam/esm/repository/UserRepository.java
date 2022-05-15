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
     * @param firstName - user firstname
     * @param lastName  - user lastname
     * @param id        - user id
     */
    @Modifying
    @Query("update User user set user.firstName = ?1, user.lastName = ?2, user.password = ?3 where user.id = ?4")
    void updateById(String firstName, String lastName, String password, Long id);
}
