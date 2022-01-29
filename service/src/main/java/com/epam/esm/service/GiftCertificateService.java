package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDto> findAll();

    GiftCertificateDto findById(Long id);

    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto updateById(Long id, GiftCertificateDto giftCertificateDto);

    void deleteById(Long id);
}
