package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Gift certificate service layer.
 * Works with gift certificate repository layer.
 */
public interface GiftCertificateService {
    /**
     * Find all gift certificates.
     *
     * @return - list of gift certificates or empty list.
     */
    List<GiftCertificate> findAll();

    /**
     * Find all gift certificates with sort column name.
     *
     * @return - sorted list of gift certificates or empty list.
     */
    List<GiftCertificate> findAllSorted(Set<ColumnName> columnNames, SortType sortType);

    /**
     * Find a gift certificate by ID.
     *
     * @param id - gift certificate ID.
     * @return - optional of found gift certificate.
     */
    Optional<GiftCertificate> findById(Long id);

    /**
     //     * Find a gift certificate by part of name or description.
     //     *
     //     * @param partname - part of the gift certificate name.
     //     * @param partDescription - part of the gift certificate description.
     //     * @return - gift certificate or empty list.
     //     */
    List<GiftCertificate> search(String partname, String partDescription);

    /**
     * Find a gift certificate by tag name.
     *
     * @param tagName - tag name.
     * @return - gift certificate.
     */
    List<GiftCertificate> findByTagName(String tagName);

    /**
     * Create a gift certificate.
     *
     * @param giftCertificate - create a gift certificate.
     * @return - created gift certificate.
     */
    GiftCertificate create(GiftCertificate giftCertificate);

    /**
     * Update a gift certificate.
     *
     * @param id - gift certificate ID.
     * @param giftCertificate - updated gift certificate.
     * @return - operation result (gift certificate updated full or partly)
     */
    GiftCertificate updateById(Long id, GiftCertificate giftCertificate);

    /**
     * Delete a gift certificate.
     *
     * @param id - gift certificate ID.
     * @return - operation result (gift certificate deleted or not)
     */
    boolean deleteById(Long id);
}
