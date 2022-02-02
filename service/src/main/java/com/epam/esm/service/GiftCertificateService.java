package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    List<GiftCertificate> findAll();

    GiftCertificate findById(Long id);

    Optional<GiftCertificate> findByPartName(String partName);

    Optional<GiftCertificate> findByPartDescription(String partDescription);

    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate updateById(Long id, GiftCertificate giftCertificateDto);

    void deleteById(Long id);
}
