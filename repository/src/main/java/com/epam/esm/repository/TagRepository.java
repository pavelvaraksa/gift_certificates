package com.epam.esm.repository;

import com.epam.esm.domain.Tag;

import java.util.Optional;
import java.util.Set;

public interface TagRepository extends CrudRepository<Long, Tag>{
    Optional<Tag> findByName(String name);

    Set<Tag> findByGiftCertificateId(Long id);

    void deleteAllTagsByGiftCertificateId(Long giftCertificateId);
}
