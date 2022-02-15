//package com.epam.esm.repository.impl;
//
//import com.epam.esm.domain.Tag;
//import com.epam.esm.repository.TagRepository;
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
// * Tag repository implementation.
// */
//@Log4j2
//@Repository
//public class TagRepositoryImpl implements TagRepository {
//    private final SessionFactory sessionFactory;
//
//    public TagRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public List<Tag> findAll() {
//        try (Session session = sessionFactory.openSession()) {
//            String hqlQuery = "select tag from Tag tag";
//            return session.createQuery(hqlQuery, Tag.class).list();
//        }
//    }
//
//    @Override
//    public Tag findById(Long id) {
//        try (Session session = sessionFactory.openSession()) {
//            return session.find(Tag.class, id);
//        }
//    }
//
//    @Override
//    public Tag create(Tag tag) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.getTransaction();
//            transaction.begin();
//            session.save(tag);
//            transaction.commit();
//            return tag;
//        }
//    }
//
//    @Override
//    public Tag updateById(Tag object) {
//        return null;
//    }
//
//    @Override
//    public boolean deleteById(Long id) {
//        try (Session session = sessionFactory.openSession()) {
//            Tag tag = session.find(Tag.class, id);
//            Transaction transaction = session.getTransaction();
//            transaction.begin();
//            session.delete(tag);
//            transaction.commit();
//            return true;
//        }
//    }
//
//
//
//
//    @Override
//    public Optional<Tag> findByName(String name) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Set<Tag> findByGiftCertificateId(Long id) {
//        return null;
//    }
//
//    @Override
//    public void deleteAllTagsByGiftCertificateId(Long giftCertificateId) {
//    }
//}
