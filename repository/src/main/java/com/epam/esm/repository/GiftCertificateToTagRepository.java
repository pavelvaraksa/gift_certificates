package com.epam.esm.repository;

import java.util.Optional;

/**
 * Gift certificate to tag repository interface layer.
 * Works with database.
 */
public interface GiftCertificateToTagRepository {
    /**
     * Create a link between gift certificate and tag in the database.
     *
     * @param giftCertificateId - gift certificate ID.
     * @param tagId - tag ID.
     * @return - operation result (link created or not)
     */
    boolean createLink(Long giftCertificateId, Long tagId);

    /**
     * Find a link between gift certificate and tag in the database.
     *
     * @param giftCertificateId - gift certificate ID.
     * @param tagId - tag ID.
     * @return - optional of found link.
     */
    Optional<GiftCertificateToTagRepository> findLink(Long giftCertificateId, Long tagId);
}
