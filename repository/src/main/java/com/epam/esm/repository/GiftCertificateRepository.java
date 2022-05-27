package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Gift certificate repository layer
 * Works with database
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, PagingAndSortingRepository<GiftCertificate, Long> {
    /**
     * Find all exist certificates
     *
     * @return - certificate list
     */
    @Query("select cert from GiftCertificate cert where cert.isActive = false")
    List<GiftCertificate> findAllCertificatesPositive(Pageable pageable);

    /**
     * Find all deleted certificates
     *
     * @return - certificate list
     */
    @Query("select cert from GiftCertificate cert where cert.isActive = true")
    List<GiftCertificate> findAllCertificatesNegative(Pageable pageable);

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
     * @param name - gift certificate name
     * @param description - gift certificate description
     * @param currentPrice - gift certificate current price
     * @param id - gift certificate id
     */
    @Modifying
    @Query("update GiftCertificate gc set gc.name = ?1, gc.description = ?2, gc.currentPrice = ?3, gc.duration = ?4 where gc.id = ?5")
    void updateById(String name, String description, Double currentPrice, Integer duration, Long id);

    /**
     * Activate gift certificate by id
     *
     * @param id - gift certificate id
     */
    @Modifying
    @Query("update GiftCertificate giftCertificate set giftCertificate.isActive = false where giftCertificate.id = ?1")
    void activateById(Long id);
}
