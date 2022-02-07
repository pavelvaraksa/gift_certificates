package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GiftCertificateService {
    List<GiftCertificate> findAll();

    List<GiftCertificate> findAllSorted(Set<ColumnName> columnNames, SortType sortType);

    Optional<GiftCertificate> findById(Long id);

    List<GiftCertificate> findByPartName(String partName);

    List<GiftCertificate> findByPartDescription(String partDescription);

    List<GiftCertificate> findByTagName(String tagName);

    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate updateById(Long id, GiftCertificate giftCertificateDto);

    boolean deleteById(Long id);
}
