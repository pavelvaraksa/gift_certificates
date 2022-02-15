//package com.epam.esm.repository.impl;
//
//import com.epam.esm.domain.GiftCertificate;
//import com.epam.esm.repository.GiftCertificateRepository;
//import com.epam.esm.util.ColumnName;
//import com.epam.esm.util.SortType;
//import lombok.extern.log4j.Log4j2;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
///**
// * Gift certificate repository implementation.
// */
//@Log4j2
//@Repository
//public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
//    private final SessionFactory sessionFactory;
//
//    public GiftCertificateRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public List<GiftCertificate> findAll() {
//        try (Session session = sessionFactory.openSession()) {
//            String hqlQuery = "select certificate from GiftCertificate certificate";
//            return session.createQuery(hqlQuery, GiftCertificate.class).list();
//        }
//    }
//
//    @Override
//    public GiftCertificate findById(Long id) {
//        try (Session session = sessionFactory.openSession()) {
//            return session.find(GiftCertificate.class, id);
//        }
//    }
//
//    @Override
//    public GiftCertificate create(GiftCertificate giftCertificate) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.getTransaction();
//            transaction.begin();
//            session.save(giftCertificate);
//            transaction.commit();
//            return giftCertificate;
//        }
//    }
//
//    @Override
//    public GiftCertificate updateById(GiftCertificate giftCertificate) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.getTransaction();
//            transaction.begin();
//            session.update(giftCertificate);
//            transaction.commit();
//            return giftCertificate;
//        }
//    }
//
//    @Override
//    public boolean deleteById(Long id) {
//        try (Session session = sessionFactory.openSession()) {
//            GiftCertificate giftCertificate = session.find(GiftCertificate.class, id);
//            Transaction transaction = session.getTransaction();
//            transaction.begin();
//            session.delete(giftCertificate);
//            transaction.commit();
//            return true;
//        }
//    }
//
//
//
//
//
//    @Override
//    public Optional<GiftCertificate> findByName(String name) {
//        return Optional.empty();
//    }
//
//    @Override
//    public List<GiftCertificate> findByPartName(String partName) {
//        return null;
//    }
//
//    @Override
//    public List<GiftCertificate> findByPartDescription(String partDescription) {
//        return null;
//    }
//
//    @Override
//    public List<GiftCertificate> findByTagId(Long id) {
//        return null;
//    }
//
//    @Override
//    public List<GiftCertificate> findAllSorted(Set<ColumnName> columnNames, SortType sortType) {
//        return null;
//    }
//}
//
