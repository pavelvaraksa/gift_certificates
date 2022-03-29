package com.epam.esm.repository;

import com.epam.esm.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Role repository layer
 * Works with database
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
