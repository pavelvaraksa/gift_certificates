package com.epam.esm.controller;

import com.epam.esm.domain.Order;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

//    /**
//     * Find list of orders
//     *
//     * @return - page of orders or empty page
//     */
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CollectionModel<OrderDto> findAllOrders(Pageable pageable, @RequestParam(value = "isDeleted",
//            required = false, defaultValue = "false") boolean isDeleted) {
//        List<Order> listOrder = orderService.findAll(pageable, isDeleted);
//        List<OrderDto> items = new ArrayList<>();
//
//        for (Order order : listOrder) {
//            OrderDto orderDto = modelMapper.map(order, OrderDto.class);
//            orderDto.add(linkTo(methodOn(OrderRestController.class).findOrderById(order.getId())).withSelfRel());
//            items.add(orderDto);
//        }
//
//        return CollectionModel.of(items, linkTo(OrderRestController.class).withRel("orders"));
//    }

    /**
     * Find order by id
     *
     * @param id - order id
     * @return - order
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<OrderDto> findOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.findById(id);
        return EntityModel.of(modelMapper.map(order.get(), OrderDto.class),
                linkTo(OrderRestController.class).slash(id).withSelfRel(),
                linkTo(OrderRestController.class).withRel("orders"));
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
    public EntityModel<OrderDto> createOrder(@RequestParam Long user,
                                             @RequestParam List<Long> certificate) {
        Order newOrder = orderService.save(user, certificate);
        return EntityModel.of(modelMapper.map(newOrder, OrderDto.class),
                linkTo(OrderRestController.class).slash(newOrder.getId()).withSelfRel(),
                linkTo(OrderRestController.class).withRel("orders"));
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
