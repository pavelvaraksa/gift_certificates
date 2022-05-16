package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.OrderDetails;
import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.OrderDetailsRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.exception.MessageException.CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.MessageException.ORDER_NOT_FOUND;
import static com.epam.esm.exception.MessageException.USER_NOT_FOUND;

/**
 * Order service implementation
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            log.error("Order with id " + id + " was not found");
            throw new ServiceNotFoundException(ORDER_NOT_FOUND);
        }

        return order;
    }

    @Transactional
    @Override
    public Order save(Long userId, List<Long> giftCertificateId) {

        if (userId == null) {
            log.error("User with id " + userId + " was not found");
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        Optional<User> user = userService.findById(userId);
        Optional<GiftCertificate> giftCertificate;
        OrderDetails orderDetails = new OrderDetails();
        Set<OrderDetails> orderDetailsSet = new HashSet<>();
        Order order = new Order();
        Double orderPrice = 0D;

        if (giftCertificateId == null) {
            log.error("User with id " + giftCertificateId + " was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        for (Long id : giftCertificateId) {
            giftCertificate = giftCertificateService.findById(id);
            Double giftCertificatePrice = giftCertificate.get().getCurrentPrice();
            order.setTotalPrice(giftCertificatePrice);
            orderPrice += giftCertificatePrice;
            order.setCount(giftCertificateId.size());
            order.setPurchaseDate(LocalDateTime.now());
            order.setCertificate(Collections.singleton(giftCertificate.get()));
            order.setUser(user.get());
        }

        order.setTotalPrice(orderPrice);
        orderRepository.save(order);

        for (Long id : giftCertificateId) {
            giftCertificate = giftCertificateService.findById(id);
            orderDetails.setActualPrice(giftCertificate.get().getCurrentPrice());
            orderDetails.setOrder(order);
            orderDetails.setCertificate(giftCertificate.get());
            orderDetailsSet.add(orderDetails);
            orderDetailsRepository.save(orderDetails);
        }

        return order;
    }

    @Override
    public Optional<User> findUserByOrderId(Long id) {
        return orderRepository.findUserByOrderId(id);
    }

    @Transactional
    @Override
    public Order deleteById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            log.error("Order was not found");
            throw new ServiceNotFoundException(ORDER_NOT_FOUND);
        }

        log.info("Order with id " + id + " deleted");
        orderRepository.deleteOrderById(id);
        return order.get();
    }
}
