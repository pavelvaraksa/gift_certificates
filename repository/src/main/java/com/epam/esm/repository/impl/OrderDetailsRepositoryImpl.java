package com.epam.esm.repository.impl;

import com.epam.esm.domain.OrderDetails;
import com.epam.esm.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

/**
 * Order details repository implementation
 */
@Repository
@RequiredArgsConstructor
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {
    private final SessionFactory sessionFactory;

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(orderDetails);
        transaction.commit();
        return orderDetails;
    }

    @Override
    public OrderDetails findById(Long id) {
        Session session = sessionFactory.openSession();
        return session.find(OrderDetails.class, id);
    }
}
