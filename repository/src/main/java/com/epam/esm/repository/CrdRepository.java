package com.epam.esm.repository;

import java.util.Optional;

/**
 * Crd service interface layer
 * Works with repository layer
 */
public interface CrdRepository<K, V> {
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
    V deleteById(K key);
}
