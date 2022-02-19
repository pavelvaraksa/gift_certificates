package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final SessionFactory sessionFactory;
    Tag tag = new Tag();

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
            session.update(giftCertificate);
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
