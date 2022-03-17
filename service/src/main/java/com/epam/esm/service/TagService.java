package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.util.ColumnTagName;
import com.epam.esm.util.SortType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Tag service layer
 * Works with tag repository layer
 */
public interface TagService {
    /**
     * Find tags with pagination, sorting and info about deleted tags
     *
     * @param pageable  - pagination config
     * @param column    - tag column
     * @param sort      - sort type
     * @param isDeleted - info about deleted tags
     * @return - list of tags or empty list
     */
    List<Tag> findAll(Pageable pageable, Set<ColumnTagName> column, SortType sort, boolean isDeleted);

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
    Tag deleteById(Long id);

    /**
     * Find most widely used tag
     *
     * @return - tag
     */
    Optional<Tag> findMostWidelyUsed();

    /**
     * Activate a tag
     *
     * @param id        - tag id
     * @param isCommand - command for activate
     * @return - activated tag
     */
    Tag activateById(Long id, boolean isCommand);
}
