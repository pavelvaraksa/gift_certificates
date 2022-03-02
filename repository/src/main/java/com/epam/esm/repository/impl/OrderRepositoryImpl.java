package com.epam.esm.repository.impl;

import com.epam.esm.domain.Order;
import com.epam.esm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Order repository implementation
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final SessionFactory sessionFactory;
    private final String FIND_ALL_QUERY = "select order from Order order";

    @Override
    public List<Order> findAll(Pageable pageable, boolean isDeleted) {
        try (Session session = sessionFactory.openSession()) {
            session.unwrap(Session.class);
            Filter filter = session.enableFilter("orderFilter");
            filter.setParameter("isDeleted", isDeleted);
            List<Order> orders = session.createQuery(FIND_ALL_QUERY, Order.class).list();
            session.disableFilter("orderFilter");
            return orders;
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Order.class, id));
        }
    }

    @Override
    public Order findByExistId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Order.class, id);
        }
    }

    @Override
    public Order save(Order order) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(order);
            transaction.commit();
            return order;
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Order order = session.find(Order.class, id);
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.delete(order);
            transaction.commit();
        }
    }
}
