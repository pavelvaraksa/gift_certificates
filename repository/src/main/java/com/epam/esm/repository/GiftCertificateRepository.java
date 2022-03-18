package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.util.ColumnCertificateName;
import com.epam.esm.util.SortType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Gift certificate repository interface layer
 * Works with database
 */
public interface GiftCertificateRepository extends CrdRepository<Long, GiftCertificate> {
    /**
     * Find gift certificates with pagination, sorting and info about deleted gift certificates
     *
     * @param pageable  - pagination config
     * @param column    - gift certificate column
     * @param sort      - sort type
     * @param isDeleted - info about deleted gift certificates
     * @return - list of tags or empty list
     */
    List<GiftCertificate> findAll(Pageable pageable, Set<ColumnCertificateName> column, SortType sort, boolean isDeleted);

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
