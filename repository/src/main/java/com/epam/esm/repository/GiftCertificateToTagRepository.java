package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificateToTag;

import java.util.Optional;

/**
 * Gift certificate to tag repository interface layer
 * Works with database
 */
public interface GiftCertificateToTagRepository {

    /**
     * Save link between gift certificate and tag
     *
     * @param giftCertificateToTag - link
     * @return - created link
     */
    GiftCertificateToTag save(GiftCertificateToTag giftCertificateToTag);

    /**
     * Find link between gift certificate and tag
     *
     * @param certificateToTag - link
     * @return - optional of found link
     */
    Optional<GiftCertificateToTag> find(GiftCertificateToTag certificateToTag);

    /**
     * Confirmation link between gift certificate and tag
     *
     * @param certificateId - gift certificate id
     * @param tagId         - tag id
     * @return - true or false
     */
    boolean isExistLink(Long certificateId, Long tagId);
}
