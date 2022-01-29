package com.epam.esm.repository;

import javax.management.ServiceNotFoundException;
import java.util.List;

public interface CrudRepository<K, V> {
    List<V> findAll();

    V findById(K key) throws ServiceNotFoundException;

    V create(V object);

    V updateById(K key, V object);

    void deleteById(K key);
}
