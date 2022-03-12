package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificateToTag;
import com.epam.esm.repository.GiftCertificateToTagRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Gift certificate to tag repository implementation
 */
@Repository
@RequiredArgsConstructor
public class GiftCertificateToTagRepositoryImpl implements GiftCertificateToTagRepository {
    private final SessionFactory sessionFactory;

    @Override
    public GiftCertificateToTag save(GiftCertificateToTag giftCertificateToTag) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(giftCertificateToTag);
        transaction.commit();
        return giftCertificateToTag;
    }

    @Override
    public Optional<GiftCertificateToTag> findByLink(GiftCertificateToTag giftCertificateToTag) {
        Session session = sessionFactory.openSession();
        return Optional.ofNullable(session.find(GiftCertificateToTag.class, giftCertificateToTag));
    }

    @Override
    public boolean isExistLink(Long certificateId, Long tagId) {
        GiftCertificateToTag giftCertificateToTag = createLink(certificateId, tagId);
        return findByLink(giftCertificateToTag).isPresent();
    }

    private GiftCertificateToTag createLink(Long certificateId, Long tagId) {
        return new GiftCertificateToTag(certificateId, tagId);
    }
}
