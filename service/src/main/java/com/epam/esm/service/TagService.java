package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Tag service layer
 * Works with tag repository layer
 */
public interface TagService extends CreateService<Tag>, ReadDeleteService<Tag, Long> {
    /**
     * Find all tags
     *
     * @return - list of tags or empty list
     */
    List<Tag> findAll(Pageable pageable);

    /**
     * Find all tags
     *
     * @return - list of tags or empty list
     */
    List<Tag> findAllForAdmin(boolean isActive, Pageable pageable);

    /**
     * Find tag by name
     *
     * @param name - tag name
     * @return - optional of found tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Activate tag
     *
     * @param id        - tag id
     * @return - activated tag
     */
    Tag activateById(Long id);

    /**
     * Find most widely used tag
     *
     * @return - tag
     */
    Tag findMostWidelyUsed();
}
