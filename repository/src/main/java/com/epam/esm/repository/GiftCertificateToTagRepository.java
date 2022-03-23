package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificateToTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    @Query("select gctt from GiftCertificateToTag gctt where gctt.giftCertificate = ?1 and gctt.tag = ?2")
    boolean isExistLink(Long certificateId, Long tagId);
}
