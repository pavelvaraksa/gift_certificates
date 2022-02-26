package com.epam.esm.repository.impl;

import com.epam.esm.domain.Order;
import com.epam.esm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    @Override
    public List<Order> findAll() {
        try (Session session = sessionFactory.openSession()) {
            String hqlQuery = "select order from Order order";
            return session.createQuery(hqlQuery, Order.class).list();
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
