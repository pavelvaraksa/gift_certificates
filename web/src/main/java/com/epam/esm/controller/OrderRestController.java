package com.epam.esm.controller;

import com.epam.esm.domain.Order;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    /**
     * Find list of orders
     *
     * @return - list of orders or empty list
     */
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllOrders(Pageable pageable, @RequestParam(value = "isDeleted",
            required = false, defaultValue = "false") boolean isDeleted) {

        List<Order> listOrder = orderService.findAll(pageable, isDeleted);
        return listOrder
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Find order by id
     *
     * @param id - order id
     * @return - order
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto findOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.findById(id);
        return modelMapper.map(order.get(), OrderDto.class);
    }

    /**
     * Create order
     *
     * @param user        - user id
     * @param certificate - certificate id
     * @return - order
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestParam Long user,
                                @RequestParam List<Long> certificate) {

        Order newOrder = orderService.save(user, certificate);
        return modelMapper.map(newOrder, OrderDto.class);
    }

    /**
     * Delete order by id
     *
     * @param id - order id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
