package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * Gift certificate repository interface layer
 * Works with database
 */
public interface GiftCertificateRepository extends CrdRepository<Long, GiftCertificate> {
    /**
     * Find all gift certificates id by order id
     *
     * @param id - order id
     * @return - list of gift certificates id or empty list
     */
    List<Long> findAllByOrderId(Long id);

    /**
     * Find gift certificate by name
     *
     * @param name - gift certificate name
     * @return - optional of found gift certificate
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Find list of gift certificates by tag id
     *
     * @param id - tag id
     * @return - list of gift certificates
     */
    List<GiftCertificate> findByTagId(Long id);

    /**
     * Update gift certificate
     *
     * @param giftCertificate - gift certificate
     * @return - updated gift certificate
     */
    GiftCertificate updateById(GiftCertificate giftCertificate);

    /**
     * Activate gift certificate by id
     *
     * @param id - gift certificate id
     * @return - activated gift certificate
     */
    GiftCertificate activateById(Long id);
}
