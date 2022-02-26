package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;

import java.util.Optional;

/**
 * Gift certificate repository interface layer
 * Works with database
 */
public interface GiftCertificateRepository extends CrdRepository<Long, GiftCertificate> {
    /**
     * Find gift certificate by name
     *
     * @param name - gift certificate name
     * @return - optional of found gift certificate
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Update gift certificate
     *
     * @param giftCertificate - gift certificate
     * @return - updated gift certificate
     */
    GiftCertificate updateById(GiftCertificate giftCertificate);
}
