package com.epam.esm.service.impl;

import com.epam.esm.domain.Order;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Order service implementation.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            log.error("order with id " + id + " was not found");
            throw new ServiceNotFoundException("not found");
        }

        return order;
    }

    @Override
    public Order save(Order order) {
        log.info("Order with id " + order.getId() + " saved");
        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            log.error("Order was not found");
            throw new ServiceNotFoundException("not found");
        }

        log.info("Order with id " + id + " deleted");
        orderRepository.deleteById(id);
    }
}
