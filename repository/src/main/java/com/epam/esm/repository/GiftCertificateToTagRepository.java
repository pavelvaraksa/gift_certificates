package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificateToTag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Gift certificate to tag repository interface layer
 * Works with database
 */
public interface GiftCertificateToTagRepository extends JpaRepository<GiftCertificateToTag, Long> {
    /**
     * Confirmation link between gift certificate and tag
     *
     * @param certificateId - gift certificate id
     * @param tagId         - tag id
     * @return - true or false
     */
    boolean existsByGiftCertificateAndTag(Long certificateId, Long tagId);
}
