package com.epam.esm.repository;

import com.epam.esm.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Role repository layer
 * Works with database
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find user role by user id
     *
     * @param id - user id
     * @return - user role
     */
    @Query(value = "select name from roles " +
            "join user_roles ur on roles.id = ur.role_id " +
            "join user_table ut on ur.user_id = ut.id " +
            "where ut.id = ?", nativeQuery = true)
    String findRoleByUserId(Long id);
}
