package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.*;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificate> findAll();

    GiftCertificateDto findById(Long id);

    GiftCertificateDto create(GiftCertificateDto giftCertificate);

    GiftCertificateDto updateById(Long id, GiftCertificateDto giftCertificate);

    void deleteById(Long id);
}
