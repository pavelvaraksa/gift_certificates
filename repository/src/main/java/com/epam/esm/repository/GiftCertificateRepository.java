package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GiftCertificateRepository extends CrudRepository<Long, GiftCertificate> {
    Optional<GiftCertificate> findByName(String name);

    List<GiftCertificate> findByPartName(String name);

    List<GiftCertificate> findByPartDescription(String name);

    List<GiftCertificate> findByTagId(Long id);

    List<GiftCertificate> findAllSorted(Set<ColumnName> columnNames, SortType sortType);
}
