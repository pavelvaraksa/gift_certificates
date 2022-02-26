package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface CrdRepository<K, V> {
    /**
     * Find all objects
     *
     * @return - list of objects or empty list
     */
    List<V> findAll();

    /**
     * Find object by id
     *
     * @param key - object id
     * @return - optional of found object
     */
    Optional<V> findById(K key);

    /**
     * Save object
     *
     * @param object- create object
     * @return - created object
     */
    V save(V object);

    /**
     * Delete object
     *
     * @param key - object id
     */
    void deleteById(K key);
}
