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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Tag repository implementation
 */
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final SessionFactory sessionFactory;
    private final String FIND_ALL_QUERY = "select tag from Tag tag";

    @Override
    public List<Tag> findAll(Pageable pageable, boolean isDeleted) {
        try (Session session = sessionFactory.openSession()) {
            session.unwrap(Session.class);
            Filter filter = session.enableFilter("tagFilter");
            filter.setParameter("isDeleted", isDeleted);
            List<Tag> tags = session.createQuery(FIND_ALL_QUERY, Tag.class).list();
            session.disableFilter("tagFilter");
            return tags;
        }
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Tag.class, id));
        }
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Criteria criteria = sessionFactory.openSession().createCriteria(Tag.class);
        criteria.add(Restrictions.like("name", name));
        return Optional.ofNullable((Tag) criteria.uniqueResult());
    }

    @Override
    public Tag save(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(tag);
            transaction.commit();
            return tag;
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Tag tag = session.find(Tag.class, id);
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.delete(tag);
            transaction.commit();
        }
    }
}
