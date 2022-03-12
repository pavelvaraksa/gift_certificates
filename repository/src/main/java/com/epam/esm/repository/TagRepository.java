package com.epam.esm.repository;

import com.epam.esm.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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

    /**
     * Find tags id by gift certificate id
     *
     * @param id - gift certificate id
     * @return - list of tags id or empty list
     */
    List<Long> findByCertificate(Long id);


    Page<Tag> findAllTags(Pageable pageable);
}
