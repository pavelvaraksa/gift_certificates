package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Gift certificate repository interface layer.
 * Works with database.
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(Long id);

    Optional<GiftCertificate> findByName(String name);

    GiftCertificate save(GiftCertificate giftCertificate);

    @Modifying
    @Query("update GiftCertificate gc set gc.name = ?1, gc.description = ?2, gc.price = ?3, gc.duration = ?4 where gc.id = ?5")
    void updateById(String name, String description, BigDecimal price, Integer duration, Long id);

    void deleteById(Long id);
}
