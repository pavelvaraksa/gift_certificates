package com.epam.esm.service;

import java.util.Optional;

/**
 * Crd interface layer
 * Works with service layer
 */
public interface CrdService<V, K> {
    /**
     * Save object
     *
     * @param object- create object
     * @return - created object
     */
    V save(V object);

    /**
     * Find object by id
     *
     * @param key - object id
     * @return - optional of found object
     */
    Optional<V> findById(K key);

    /**
     * Delete object
     *
     * @param key - object id
     * @return - deleted object
     */
    V deleteById(K key);
}
