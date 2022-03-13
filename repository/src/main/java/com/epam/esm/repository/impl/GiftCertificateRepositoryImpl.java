package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.util.ColumnCertificateName;
import com.epam.esm.util.SortType;
import com.epam.esm.util.SqlCertificateQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Gift certificate repository implementation
 */
@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final SessionFactory sessionFactory;
    private final String FIND_ALL_QUERY_BY_TAG_ID = "select gc from GiftCertificate gc " +
            "join GiftCertificateToTag gctt on gc.id = gctt.giftCertificate where gctt.tag = ";
    private final String FIND_ALL_QUERY_BY_ORDER_ID = "select gc.id from GiftCertificate gc " +
            "join OrderDetails od on gc.id = od.certificate where od.order = ";

    @Override
    public List<GiftCertificate> findAll(Pageable pageable, Set<ColumnCertificateName> column, SortType sort, boolean isDeleted) {
        Session session = sessionFactory.openSession();
        session.unwrap(Session.class);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Filter filter = session.enableFilter("tagFilter");
        filter.setParameter("isDeleted", isDeleted);
        String sqlQuery = SqlCertificateQuery.findAllSorted(column, sort);
        Query queryCertificates = session.createQuery(sqlQuery, GiftCertificate.class);
        queryCertificates.setFirstResult(pageNumber * pageSize);
        queryCertificates.setMaxResults(pageSize);
        List<GiftCertificate> list = queryCertificates.getResultList();
        session.disableFilter("tagFilter");
        return list;
    }

    @Override
    public List<Long> findAllByOrderId(Long id) {
        Session session = sessionFactory.openSession();
        return session.createQuery(FIND_ALL_QUERY_BY_ORDER_ID + id).list();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        Session session = sessionFactory.openSession();
        return Optional.ofNullable(session.find(GiftCertificate.class, id));
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Criteria criteria = sessionFactory.openSession().createCriteria(GiftCertificate.class);
        criteria.add(Restrictions.like("name", name));
        return Optional.ofNullable((GiftCertificate) criteria.uniqueResult());
    }

    @Override
    public List<GiftCertificate> findByTagId(Long id) {
        Session session = sessionFactory.openSession();
        return session.createQuery(FIND_ALL_QUERY_BY_TAG_ID + id, GiftCertificate.class).list();
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(giftCertificate);
        transaction.commit();
        return giftCertificate;
    }

    @Override
    public GiftCertificate updateById(GiftCertificate giftCertificate) {
        Session session = sessionFactory.openSession();
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

    @Override
    public GiftCertificate activateById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        GiftCertificate activatedGiftCertificate = session.find(GiftCertificate.class, id);
        activatedGiftCertificate.setActive(!activatedGiftCertificate.isActive());
        session.merge(activatedGiftCertificate);
        transaction.commit();
        return activatedGiftCertificate;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        GiftCertificate giftCertificate = session.find(GiftCertificate.class, id);
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(giftCertificate);
        transaction.commit();
    }
}
