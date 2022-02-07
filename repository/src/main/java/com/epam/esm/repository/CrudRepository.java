package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

/**
 * CRUD repository interface layer.
 * Works with database.
 */
public interface CrudRepository<K, V> {
    /**
     * Find all objects.
     *
     * @return - List of objects or empty list.
     */
    List<V> findAll();

    /**
     * Find an object by ID.
     *
     * @param key - object ID.
     * @return - optional of found object.
     */
    Optional<V> findById(K key);

    /**
     * Create an object in the database.
     *
     * @param object - create an object.
     * @return - created object.
     */
    V create(V object);

    /**
     * Update an object in the database.
     *
     * @param key - object ID.
     * @param object - updated object.
     * @return - operation result (object updated full or partly)
     */
    V updateById(K key, V object);

    /**
     * Delete an object in the database.
     *
     * @param key - object ID.
     * @return - operation result (object deleted or not)
     */
    boolean deleteById(K key);
}
