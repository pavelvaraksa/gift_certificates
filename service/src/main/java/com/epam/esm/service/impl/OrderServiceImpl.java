package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.MessageException.ORDER_NOT_FOUND;

/**
 * Order service implementation.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
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
            log.error("order with id " + id + " was not found");
            throw new ServiceNotFoundException(ORDER_NOT_FOUND);
        }

        return order;
    }

    @Override
    public Order save(Long userId, List<Long> giftCertificateId) {
        Optional<User> user = userService.findById(userId);
        Optional<GiftCertificate> giftCertificate;
        Order order = new Order();

        for (Long id : giftCertificateId) {
            giftCertificate = giftCertificateService.findById(id);

            BigDecimal giftCertificatePrice = giftCertificate.get().getPrice();
            order.setPrice(giftCertificatePrice);
            order.setPurchaseDate(LocalDateTime.now());
            order.setGiftCertificate(giftCertificate.get());
            order.setUser(user.get());
            orderRepository.save(order);
        }

        return order;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            log.error("Order was not found");
            throw new ServiceNotFoundException(ORDER_NOT_FOUND);
        }

        log.info("Order with id " + id + " deleted");
        orderRepository.deleteById(id);
    }
}
