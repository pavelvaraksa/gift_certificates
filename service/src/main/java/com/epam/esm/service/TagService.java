package com.epam.esm.service;

import com.epam.esm.domain.Tag;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * Tag service layer
 * Works with tag repository layer
 */
public interface TagService {
    /**
     * Find all tags
     *
     * @return - list of tags or empty list
     */
    List<Tag> findAll(Pageable pageable, boolean isDeleted);

    /**
     * Find a tag by id.
     *
     * @param id - tag id
     * @return - optional of found tag
     */
    Optional<Tag> findById(Long id);

    /**
     * Find a tag name
     *
     * @param name - tag name
     * @return - optional of found tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Create a tag
     *
     * @param tag - create a tag
     * @return - created tag
     */
    Tag save(Tag tag);

    /**
     * Delete a tag
     *
     * @param id - tag id
     */
    void deleteById(Long id);
}
