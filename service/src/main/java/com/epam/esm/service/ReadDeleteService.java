package com.epam.esm.service;

import java.util.Optional;

/**
 * Read/delete interface layer
 * Works with service layer
 */
public interface ReadDeleteService<V, K> {
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
