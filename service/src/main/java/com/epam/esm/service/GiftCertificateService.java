package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.util.ColumnCertificateName;
import com.epam.esm.util.SortType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Gift certificate service layer
 * Works with gift certificate repository layer
 */
public interface GiftCertificateService {
    /**
     * Find gift certificates with pagination, sorting and info about deleted gift certificates
     *
     * @param pageable  - pagination config
     * @param column    - certificate column
     * @param sort      - sort type
     * @param isDeleted - info about deleted certificates
     * @return - list of gift certificates or empty list
     */
    List<GiftCertificate> findAll(Pageable pageable, Set<ColumnCertificateName> column, SortType sort, boolean isDeleted);

    /**
     * Find all gift certificates id by order id
     *
     * @param id - order id
     * @return - list of gift certificates id or empty list
     */
    List<Long> findAllIdByOrderId(Long id);

    /**
     * Find a gift certificate by id
     *
     * @param id - gift certificate id
     * @return - optional of found gift certificate
     */
    Optional<GiftCertificate> findById(Long id);

    /**
     * Find a gift certificate by name
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
     * Create a gift certificate
     *
     * @param giftCertificate - create a gift certificate
     * @return - created gift certificate
     */
    GiftCertificate save(GiftCertificate giftCertificate);

    /**
     * Update a gift certificate
     *
     * @param id              - gift certificate id
     * @param giftCertificate - updated gift certificate
     * @return - operation result (gift certificate updated full or partly)
     */
    GiftCertificate updateById(Long id, GiftCertificate giftCertificate);

    /**
     * Activate a gift certificate
     *
     * @param id        - gift certificate id
     * @param isCommand - command for activate
     * @return - activated gift certificate
     */
    GiftCertificate activateById(Long id, boolean isCommand);

    /**
     * Delete a gift certificate
     *
     * @param id - gift certificate id
     */
    void deleteById(Long id);
}
