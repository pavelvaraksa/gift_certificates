package com.epam.esm.repository.impl;

import com.epam.esm.domain.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.util.ColumnUserName;
import com.epam.esm.util.SortType;
import com.epam.esm.util.SqlUserQuery;
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
 * User repository implementation
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;
    private final String FIND_ALL_QUERY_ID = "select user.id from User user";

    @Override
    public List<User> findAll(Pageable pageable, Set<ColumnUserName> column, SortType sort, boolean isDeleted) {
        Session session = sessionFactory.openSession();
        session.unwrap(Session.class);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Filter filter = session.enableFilter("userFilter");
        filter.setParameter("isDeleted", isDeleted);
        String sqlQuery = SqlUserQuery.findAllSorted(column, sort);
        Query queryUsers = session.createQuery(sqlQuery, User.class);
        queryUsers.setFirstResult(pageNumber * pageSize);
        queryUsers.setMaxResults(pageSize);
        List<User> list = queryUsers.getResultList();
        session.disableFilter("userFilter");
        return list;
    }

    @Override
    public Optional<User> findById(Long id) {
        Session session = sessionFactory.openSession();
        return Optional.ofNullable(session.find(User.class, id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Criteria criteria = sessionFactory.openSession().createCriteria(User.class);
        criteria.add(Restrictions.like("login", login));
        return Optional.ofNullable((User) criteria.uniqueResult());
    }

    @Override
    public User save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(user);
        transaction.commit();
        return user;
    }

    @Override
    public User updateById(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.merge(user);
        transaction.commit();
        return user;
    }

    @Override
    public User activateById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        User activatedUser = session.find(User.class, id);
        activatedUser.setActive(!activatedUser.isActive());
        session.merge(activatedUser);
        transaction.commit();
        return activatedUser;
    }

    @Override
    public User deleteById(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.find(User.class, id);
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(user);
        transaction.commit();
        return user;
    }
}
