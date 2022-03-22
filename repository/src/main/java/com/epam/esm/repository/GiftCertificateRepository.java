package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Gift certificate repository layer
 * Works with database
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
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
    @Query("select gc from GiftCertificate gc join GiftCertificateToTag gctt on gc.id = gctt.giftCertificate where gctt.tag = ?1")
    List<GiftCertificate> findByTagId(Long id);

    /**
     * Update gift certificate by id
     *
     * @param giftCertificate - gift certificate
     * @return - updated gift certificate
     */
    @Modifying
    @Query("update GiftCertificate gc set gc.name = ?1, gc.description = ?2, gc.currentPrice = ?3, gc.duration = ?4 where gc.id = ?5")
    GiftCertificate updateById(GiftCertificate giftCertificate);

    /**
     * Activate gift certificate by id
     *
     * @param id - gift certificate id
     * @return - activated gift certificate
     */
    @Modifying
    @Query("update GiftCertificate giftCertificate set giftCertificate.isActive = false where giftCertificate.id = ?1")
    GiftCertificate activateById(Long id);
}
