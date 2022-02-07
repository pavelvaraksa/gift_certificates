package service;

import com.epam.esm.domain.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Tag service layer.
 * Works with tag repository layer.
 */
public interface TagService {
    /**
     * Find all tags.
     *
     * @return - list of tags or empty list.
     */
    List<Tag> findAll();

    /**
     * Find a tag by ID.
     *
     * @param id - tag ID.
     * @return - optional of found tag.
     */
    Optional<Tag> findById(Long id);

    /**
     * Create a tag.
     *
     * @param tag - create a tag.
     * @return - created tag.
     */
    Tag create(Tag tag);

    /**
     * Delete a tag.
     *
     * @param id - tag ID.
     * @return - operation result (tag deleted or not)
     */
    boolean deleteById(Long id);
}
