package com.epam.esm.controller;

import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderSaveDto;
import com.epam.esm.exception.ServiceForbiddenException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.MessageException.USER_RESOURCE_FORBIDDEN;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    public final UserService userService;
    private final RoleRepository roleRepository;

    /**
     * Find all orders
     *
     * @return - list of orders or empty list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDto> findAllOrders() {
        List<Order> listOrder = orderService.findAll();
        List<OrderDto> items = new ArrayList<>();

        for (Order order : listOrder) {
            OrderDto orderDto = modelMapper.map(order, OrderDto.class);
            orderDto.add(linkTo(methodOn(OrderRestController.class).findOrderById(order.getId())).withRel("find by id"),
                    linkTo(methodOn(OrderRestController.class).deleteOrder(order.getId())).withRel("delete by id"));
            items.add(orderDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(OrderRestController.class)
                .findAllOrders()).withRel("find all orders"));
    }

    /**
     * Find order by id
     *
     * @param id - order id
     * @return - order
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<OrderDto> findOrderById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findByLogin(currentPrincipalName);
        String role = roleRepository.findRoleByUserId(user.get().getId());
        Optional<User> searchUserByOrder = orderService.findUserByOrderId(id);
        Optional<Order> order;

        if (role.equals("ROLE_USER") && searchUserByOrder.isPresent()) {
            order = orderService.findById(id);
        } else if (role.equals("ROLE_ADMIN")) {
            order = orderService.findById(id);
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }

        return EntityModel.of(modelMapper.map(order.get(), OrderDto.class),
                linkTo(methodOn(OrderRestController.class).findOrderById(order.get().getId())).withRel("find by id"),
                linkTo(methodOn(OrderRestController.class).deleteOrder(order.get().getId())).withRel("delete by id"));
    }

    /**
     * Create order
     *
     * @param orderdata - user id and certificate id
     * @return - order
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<OrderSaveDto> createOrder(@RequestBody OrderData orderdata) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findByLogin(currentPrincipalName);
        String role = roleRepository.findRoleByUserId(user.get().getId());
        Order newOrder;

        if (role.equals("ROLE_USER") && user.get().getId().equals(orderdata.userId)) {
            newOrder = orderService.save(orderdata.userId, orderdata.certificateId);
        } else if (role.equals("ROLE_ADMIN")) {
            newOrder = orderService.save(orderdata.userId, orderdata.certificateId);
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }

        return EntityModel.of(modelMapper.map(newOrder, OrderSaveDto.class),
                linkTo(methodOn(OrderRestController.class).findOrderById(newOrder.getId())).withRel("find by id"),
                linkTo(methodOn(OrderRestController.class).deleteOrder(newOrder.getId())).withRel("delete by id"));
    }

    /**
     * Delete order by id
     *
     * @param id - order id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<OrderDto> deleteOrder(@PathVariable Long id) {
        Order deletedOrder = orderService.deleteById(id);
        return EntityModel.of(modelMapper.map(deletedOrder, OrderDto.class));
    }

    private static class OrderData {
        public Long userId;
        public List<Long> certificateId;
    }
}
