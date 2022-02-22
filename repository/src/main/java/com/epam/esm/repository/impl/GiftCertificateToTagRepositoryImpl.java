package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateToTagRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Gift certificate to tag repository implementation
 */
@Repository
@RequiredArgsConstructor
public class GiftCertificateToTagRepositoryImpl implements GiftCertificateToTagRepository {

    @Override
    public boolean createLink(Long giftCertificateId, Long tagId) {
        return false;
    }

    @Override
    public Optional<GiftCertificateToTagRepository> findLink(Long giftCertificateId, Long tagId) {
        return Optional.empty();
    }
}
