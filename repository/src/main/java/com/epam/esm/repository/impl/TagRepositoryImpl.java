package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.ColumnTagName;
import com.epam.esm.util.SortType;
import com.epam.esm.util.SqlTagQuery;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Tag repository implementation
 */
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final SessionFactory sessionFactory;
    private final String FIND_ALL_QUERY_ID_BY_CERTIFICATE_ID = "select tag.id from Tag tag " +
            "join GiftCertificateToTag gctt on tag.id = gctt.tag where gctt.giftCertificate = ";
    private final String FIND_ALL_QUERY_TAG_BY_CERTIFICATE_ID = "select tag from Tag tag " +
            "join GiftCertificateToTag gctt on tag.id = gctt.tag where gctt.giftCertificate = ";

    @Override
    public List<Tag> findAll(Pageable pageable, Set<ColumnTagName> column, SortType sort, boolean isDeleted) {
        Session session = sessionFactory.openSession();
        session.unwrap(Session.class);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Filter filter = session.enableFilter("tagFilter");
        filter.setParameter("isDeleted", isDeleted);
        String sqlQuery = SqlTagQuery.findAllSorted(column, sort);
        Query queryTags = session.createQuery(sqlQuery, Tag.class);
        queryTags.setFirstResult(pageNumber * pageSize);
        queryTags.setMaxResults(pageSize);
        List<Tag> list = queryTags.getResultList();
        session.disableFilter("tagFilter");
        return list;
    }

    @Override
    public List<Long> findAllIdByCertificateId(Long id) {
        Session session = sessionFactory.openSession();
        return session.createQuery(FIND_ALL_QUERY_ID_BY_CERTIFICATE_ID + id).list();
    }

    @Override
    public List<Tag> findAllByCertificateId(Long id) {
        Session session = sessionFactory.openSession();
        return session.createQuery(FIND_ALL_QUERY_TAG_BY_CERTIFICATE_ID + id).list();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Session session = sessionFactory.openSession();
        return Optional.ofNullable(session.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Criteria criteria = sessionFactory.openSession().createCriteria(Tag.class);
        criteria.add(Restrictions.like("name", name));
        return Optional.ofNullable((Tag) criteria.uniqueResult());
    }

    @Override
    public Tag save(Tag tag) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(tag);
        transaction.commit();
        return tag;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Tag tag = session.find(Tag.class, id);
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(tag);
        transaction.commit();
    }
}
