package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Gift certificate repository interface layer.
 * Works with database.
 */
public interface GiftCertificateRepository extends CrudRepository<Long, GiftCertificate> {
    /**
     * Find a gift certificate by name.
     *
     * @param name - gift certificate name.
     * @return - optional of found gift certificate.
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Find a gift certificate by part of name.
     *
     * @param partName - part of the gift certificate name.
     * @return - gift certificate or some gift certificates.
     */
    List<GiftCertificate> findByPartName(String partName);

    /**
     * Find a gift certificate by part of description.
     *
     * @param partDescription - part of the gift certificate description.
     * @return - gift certificate or some gift certificates.
     */
    List<GiftCertificate> findByPartDescription(String partDescription);

    /**
     * Find a gift certificate by tag ID.
     *
     * @param id - tag ID.
     * @return - gift certificate.
     */
    List<GiftCertificate> findByTagId(Long id);

    /**
     * Find all gift certificates with sort column name.
     *
     * @param columnNames - sort column name.
     * @param sortType    - sort method.
     * @return - sorted list of gift certificates or empty list.
     */
    List<GiftCertificate> findAllSorted(Set<ColumnName> columnNames, SortType sortType);
}
