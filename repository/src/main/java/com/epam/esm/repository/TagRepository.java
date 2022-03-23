package com.epam.esm.repository;

import com.epam.esm.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Tag repository layer
 * Works with database
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    /**
     * Find tags by gift certificate id
     *
     * @param id - gift certificate id
     * @return - list of tags or empty list
     */
    @Query("select tag from Tag tag join GiftCertificateToTag gctt on tag.id = gctt.tag where gctt.giftCertificate = ?1")
    List<Tag> findAllByCertificateId(Long id);

    /**
     * Find tag by name
     *
     * @param name - tag name
     * @return - optional of found tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Activate tag by id
     *
     * @param id - tag id
     */
    @Modifying
    @Query("update Tag tag set tag.isActive = false where tag.id = ?1")
    void activateById(Long id);

    /**
     * Find most widely used tag
     *
     * @return - tag
     */
    @Query(value = "select tg.id, tg.name, tg.deleted from tag tg " +
            "join gift_certificate_to_tag gctt on tg.id = gctt.tag_id " +
            "join gift_certificate gc on gc.id = gctt.gift_certificate_id " +
            "join order_details od on gc.id = od.gift_certificate_id " +
            "join order_table ot on ot.id = od.order_id " +
            "group by tg.id order by count(tg.id) desc, max(ot.price) desc limit 1", nativeQuery = true)
    Tag findMostWidelyUsed();
}


