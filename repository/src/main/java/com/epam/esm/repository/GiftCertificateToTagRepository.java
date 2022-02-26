package com.epam.esm.repository;

import java.util.Optional;

/**
 * Gift certificate to tag repository interface layer
 * Works with database
 */
public interface GiftCertificateToTagRepository {
    /**
     * Create link between gift certificate and tag
     *
     * @param giftCertificateId - gift certificate id
     * @param tagId - tag id
     * @return - operation result (link created or not)
     */
    boolean createLink(Long giftCertificateId, Long tagId);

    /**
     * Find link between gift certificate and tag
     *
     * @param giftCertificateId - gift certificate id
     * @param tagId - tag id
     * @return - optional of found link
     */
    Optional<GiftCertificateToTagRepository> findLink(Long giftCertificateId, Long tagId);
}
