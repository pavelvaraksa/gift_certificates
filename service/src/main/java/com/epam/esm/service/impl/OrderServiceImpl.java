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
import com.epam.esm.util.ColumnOrderName;
import com.epam.esm.util.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.exception.MessageException.ORDER_NOT_FOUND;
import static com.epam.esm.exception.MessageException.TAG_NOT_FOUND;

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
    public List<Order> findAll(Pageable pageable, Set<ColumnOrderName> column, SortType sort, boolean isDeleted) {
        return orderRepository.findAll(pageable, column, sort, isDeleted);
    }

    @Override
    public Long findIdWithHighestCost(List<Long> id) {
        List<Order> listAllOrders = new ArrayList<>();
        List<Order> listOrders;

        for (Long userId : id) {
            listOrders = orderRepository.findAllOrdersByUserId(userId);
            listAllOrders.addAll(listOrders);
        }

        if (listAllOrders.isEmpty()) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        Optional<Order> optionalOrder = Optional.ofNullable(listAllOrders
                .stream()
                .max(Comparator.comparing(Order::getTotalPrice))
                .get());

        return optionalOrder.get().getId();
    }

    @Override
    public Optional<Order> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            log.error("Order with id " + id + " was not found");
            throw new ServiceNotFoundException(ORDER_NOT_FOUND);
        }

        if (order.get().isActive()) {
            throw new ServiceNotFoundException(ORDER_NOT_FOUND);
        }

        return order;
    }

    @Override
    public Order save(Long userId, List<Long> giftCertificateId) {
        Optional<User> user = userService.findById(userId);
        Optional<GiftCertificate> giftCertificate;
        OrderDetails orderDetails = new OrderDetails();
        Order order = new Order();
        Double orderPrice = 0D;

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
            orderDetailsRepository.save(orderDetails);
        }

        Long orderId = order.getId();
        order = orderRepository.findByExistId(orderId);
        return order;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            log.error("Order was not found");
            throw new ServiceNotFoundException(ORDER_NOT_FOUND);
        }

        if (order.get().isActive()) {
            throw new ServiceNotFoundException(ORDER_NOT_FOUND);
        }

        log.info("Order with id " + id + " deleted");
        orderRepository.deleteById(id);
    }
}
