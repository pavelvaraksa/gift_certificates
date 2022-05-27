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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public CollectionModel<OrderDto> findAllOrders(@PageableDefault(sort = {"id"}) Pageable pageable) {
        List<Order> listOrder = orderService.findAll(pageable);
        List<OrderDto> items = new ArrayList<>();

        for (Order order : listOrder) {
            OrderDto orderDto = modelMapper.map(order, OrderDto.class);
            orderDto.add(linkTo(methodOn(OrderRestController.class).findOrderById(order.getId())).withRel("find by id"),
                    linkTo(methodOn(OrderRestController.class).deleteOrder(order.getId())).withRel("delete by id"));
            items.add(orderDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(OrderRestController.class)
                .findAllOrders(pageable)).withRel("find all orders"));
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
            return takeHateoasForUser(order.get());
        } else if (role.equals("ROLE_ADMIN")) {
            order = orderService.findById(id);
            return takeHateoasForAdmin(order.get());
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
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
            return takeHateoasSaveForUser(newOrder);
        } else if (role.equals("ROLE_ADMIN")) {
            newOrder = orderService.save(orderdata.userId, orderdata.certificateId);
            return takeHateoasSaveForAdmin(newOrder);
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
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

    private EntityModel<OrderSaveDto> takeHateoasSaveForAdmin(Order order) {
        return EntityModel.of(modelMapper.map(order, OrderSaveDto.class),
                linkTo(methodOn(OrderRestController.class).findOrderById(order.getId())).withRel("find by id"),
                linkTo(methodOn(OrderRestController.class).deleteOrder(order.getId())).withRel("delete by id"));
    }

    private EntityModel<OrderDto> takeHateoasForAdmin(Order order) {
        return EntityModel.of(modelMapper.map(order, OrderDto.class),
                linkTo(methodOn(OrderRestController.class).findOrderById(order.getId())).withRel("find by id"),
                linkTo(methodOn(OrderRestController.class).deleteOrder(order.getId())).withRel("delete by id"));
    }

    private EntityModel<OrderSaveDto> takeHateoasSaveForUser(Order order) {
        return EntityModel.of(modelMapper.map(order, OrderSaveDto.class),
                linkTo(methodOn(OrderRestController.class).findOrderById(order.getId())).withRel("find by id"));
    }

    private EntityModel<OrderDto> takeHateoasForUser(Order order) {
        return EntityModel.of(modelMapper.map(order, OrderDto.class),
                linkTo(methodOn(OrderRestController.class).findOrderById(order.getId())).withRel("find by id"));
    }
}
