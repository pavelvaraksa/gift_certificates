package com.epam.esm.repository;

import com.epam.esm.domain.Tag;
import com.epam.esm.util.ColumnTagName;
import com.epam.esm.util.SortType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Tag repository interface layer
 * Works with database
 */
public interface TagRepository extends CrdRepository<Long, Tag> {
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
     * Find tags by gift certificate id
     *
     * @param id - gift certificate id
     * @return - list of tags or empty list
     */
    List<Tag> findAllByCertificateId(Long id);

    /**
     * Find tags id by gift certificate id
     *
     * @param id - gift certificate id
     * @return - list of tags id or empty list
     */
    List<Long> findAllIdByCertificateId(Long id);

    /**
     * Find tag by name
     *
     * @param name - tag name
     * @return - optional of found tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Activate tag by id
     *
     * @param id - tag id
     * @return - activated tag
     */
    Tag activateById(Long id);
}
