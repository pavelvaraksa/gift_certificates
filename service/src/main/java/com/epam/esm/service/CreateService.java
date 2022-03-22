package com.epam.esm.service;

/**
 * Create interface layer
 * Works with service layer
 */
public interface CreateService<V> {
    /**
     * Create object
     *
     * @param object- create object
     * @return - created object
     */
    V save(V object);
}
