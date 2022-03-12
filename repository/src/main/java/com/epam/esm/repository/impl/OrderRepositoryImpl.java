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

import javax.persistence.Query;
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
    private final String FIND_ALL_ID_QUERY_BY_USER_ID = "select order from Order order " +
            "join User user on order.user = user.id where user.id = ";

    @Override
    public List<Order> findAll(Pageable pageable, boolean isDeleted) {
        Session session = sessionFactory.openSession();
        session.unwrap(Session.class);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Filter filter = session.enableFilter("orderFilter");
        filter.setParameter("isDeleted", isDeleted);
        Query queryOrders = session.createQuery(FIND_ALL_QUERY, Order.class);
        queryOrders.setFirstResult(pageNumber * pageSize);
        queryOrders.setMaxResults(pageSize);
        List<Order> list = queryOrders.getResultList();
        session.disableFilter("orderFilter");
        return list;
    }

    @Override
    public List<Order> findAllOrdersByUserId(Long id) {
        Session session = sessionFactory.openSession();
        return session.createQuery(FIND_ALL_ID_QUERY_BY_USER_ID + id).list();
    }

    @Override
    public Optional<Order> findById(Long id) {
        Session session = sessionFactory.openSession();
        return Optional.ofNullable(session.find(Order.class, id));
    }

    @Override
    public Order findByExistId(Long id) {
        Session session = sessionFactory.openSession();
        return session.find(Order.class, id);
    }

    @Override
    public Order save(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(order);
        transaction.commit();
        return order;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Order order = session.find(Order.class, id);
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(order);
        transaction.commit();
    }
}
