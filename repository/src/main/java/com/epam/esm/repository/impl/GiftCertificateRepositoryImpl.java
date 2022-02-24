package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<GiftCertificate> findAll() {
        try (Session session = sessionFactory.openSession()) {
            String hqlQuery = "select gc from GiftCertificate gc";
            return session.createQuery(hqlQuery, GiftCertificate.class).list();
        }
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(GiftCertificate.class, id));
        }
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Criteria criteria = sessionFactory.openSession().createCriteria(GiftCertificate.class);
        criteria.add(Restrictions.like("name", name));
        return Optional.ofNullable((GiftCertificate) criteria.uniqueResult());
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(giftCertificate);
            transaction.commit();
            return giftCertificate;
        }
    }

    @Override
    public GiftCertificate updateById(GiftCertificate giftCertificate) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            GiftCertificate updatedGiftCertificate = session.find(GiftCertificate.class, giftCertificate.getId());
            updatedGiftCertificate.setName(giftCertificate.getName());
            updatedGiftCertificate.setDescription(giftCertificate.getDescription());
            updatedGiftCertificate.setCurrentPrice(giftCertificate.getCurrentPrice());
            updatedGiftCertificate.setDuration(giftCertificate.getDuration());
            updatedGiftCertificate.setLastUpdateDate(LocalDateTime.now());
            session.merge(updatedGiftCertificate);
            transaction.commit();
            return giftCertificate;
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            GiftCertificate giftCertificate = session.find(GiftCertificate.class, id);
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.delete(giftCertificate);
            transaction.commit();
        }
    }
}
