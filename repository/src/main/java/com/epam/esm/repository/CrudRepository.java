package com.epam.esm.repository;

import java.util.List;

public interface CrudRepository<K, V> {
    List<V> findAll();

    V findById(K key);

    V create(V object);

    V updateById(K key, V object);

    void deleteById(K key);
}
