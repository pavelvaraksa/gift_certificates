package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Gift certificate service layer
 * Works with gift certificate repository layer
 */
public interface GiftCertificateService extends CreateService<GiftCertificate>, ReadDeleteService<GiftCertificate, Long> {
    /**
     * Find all gift certificates
     *
     * @return - list of gift certificates or empty list
     */
    List<GiftCertificate> findAll(Pageable pageable);

    /**
     * Find all gift certificates
     *
     * @return - list of gift certificates or empty list
     */
    List<GiftCertificate> findAllForAdmin(boolean isActive, Pageable pageable);

    /**
     * Find gift certificate by name
     *
     * @param name - gift certificate name
     * @return - optional of found gift certificate
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Find all gift certificates by tag name
     *
     * @param name- tag name
     * @return - list of gift certificates
     */
    List<GiftCertificate> findByTagName(List<String> name);

    /**
     * Update gift certificate
     *
     * @param id              - gift certificate id
     * @param giftCertificate - updated gift certificate
     * @return - operation result (gift certificate updated full or partly)
     */
    GiftCertificate updateById(Long id, GiftCertificate giftCertificate);

    /**
     * Activate gift certificate
     *
     * @param id - gift certificate id
     * @return - activated gift certificate
     */
    GiftCertificate activateById(Long id);
}
