package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Tag repository implementation
 */
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final SessionFactory sessionFactory;
    private final String FIND_ALL_QUERY = "select tag from Tag tag";
    private final String FIND_ALL_QUERY_BY_CERTIFICATE_ID = "select tag.id from Tag tag " +
            "join GiftCertificateToTag gctt on tag.id = gctt.tag where gctt.giftCertificate = ";

    @Override
    public List<Tag> findAll(Pageable pageable, boolean isDeleted) {
        Session session = sessionFactory.openSession();
        session.unwrap(Session.class);
//        int pageNumber = pageable.getPageNumber();
//        int pageSize = pageable.getPageSize();
//        Sort sort = pageable.getSort();
        Filter filter = session.enableFilter("tagFilter");
        filter.setParameter("isDeleted", isDeleted);
        Query queryTags = session.createQuery(FIND_ALL_QUERY, Tag.class);
//        queryTags.setFirstResult(pageNumber * pageSize);
//        queryTags.setMaxResults(pageSize);
        List<Tag> list = queryTags.getResultList();
        session.disableFilter("tagFilter");
        return list;
    }

    @Override
    public Page<Tag> findAllTags(Pageable pageable) {
        Session session = sessionFactory.openSession();
        List<Tag> list = session.createQuery(FIND_ALL_QUERY, Tag.class).list();

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Sort sort = pageable.getSort();
        Pageable pageable1 = PageRequest.of(pageNumber, pageSize, sort);

        return new PageImpl<>(list);
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
    public List<Long> findByCertificate(Long id) {
        Session session = sessionFactory.openSession();
        return session.createQuery(FIND_ALL_QUERY_BY_CERTIFICATE_ID + id).list();
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
