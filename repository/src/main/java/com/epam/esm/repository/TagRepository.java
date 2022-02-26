package com.epam.esm.repository;

import com.epam.esm.domain.Tag;

import java.util.Optional;

/**
 * Tag repository interface layer
 * Works with database
 */
public interface TagRepository extends CrdRepository<Long, Tag> {
    /**
     * Find tag by name
     *
     * @param name - tag name
     * @return - optional of found tag
     */
    Optional<Tag> findByName(String name);
}
