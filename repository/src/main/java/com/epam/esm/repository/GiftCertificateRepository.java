package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository extends CrudRepository<Long, GiftCertificate> {
    Optional<GiftCertificate> findByName(String name);

    Optional<GiftCertificate> findByPartName(String name);

    Optional<GiftCertificate> findByPartDescription(String name);

    List<GiftCertificate> findByTagId(Long id);
}
