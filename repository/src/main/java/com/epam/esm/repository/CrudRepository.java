package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<K, V> {
    List<V> findAll();

    Optional<V> findById(K key);

    V create(V object);

    V updateById(K key, V object);

    boolean deleteById(K key);
}
