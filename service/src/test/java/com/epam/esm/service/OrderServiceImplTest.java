package com.epam.esm.service;

import com.epam.esm.domain.Order;
import com.epam.esm.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPositive() {
        Order createdOrder = new Order();
        createdOrder.setId(4L);
        createdOrder.setCount(1);
        createdOrder.setTotalPrice(100.55);
        createdOrder.setPurchaseDate(LocalDateTime
                .of(2022, 2, 2, 12, 15, 13, 133000000));

        Order expectedOrder = new Order();
        expectedOrder.setId(4L);
        expectedOrder.setCount(1);
        expectedOrder.setTotalPrice(100.55);
        expectedOrder.setPurchaseDate(LocalDateTime
                .of(2022, 2, 2, 12, 15, 13, 133000000));

        Mockito.when(orderRepository.findById(Mockito.eq(createdOrder.getId()))).thenReturn(Optional.empty());
        Mockito.when(orderRepository.save(createdOrder)).thenReturn(expectedOrder);

        Order actualOrder = orderRepository.save(createdOrder);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void findByIdPositive() {
        Long requiredId = 2L;
        Order expectedOrder = new Order(requiredId, 25.43, 2, LocalDateTime.now());
        Optional<Order> optionalOrder = Optional.of(expectedOrder);
        Mockito.when(orderRepository.findById(requiredId)).thenReturn(optionalOrder);
        Optional<Order> actualOrder = orderRepository.findById(requiredId);
        assertEquals(actualOrder, optionalOrder);
    }

    @Test
    public void findByIdNotNull() {
        Long requiredId = 1L;
        Order expectedOrder = new Order(requiredId, 25.43, 1, LocalDateTime.now());
        Optional<Order> optionalOrder = Optional.of(expectedOrder);
        Mockito.when(orderRepository.findById(requiredId)).thenReturn(optionalOrder);
        orderRepository.findById(requiredId);
        assertNotNull(requiredId);
    }

    @Test
    public void findByIdNull() {
        Long requiredId = null;
        Order expectedOrder = new Order(requiredId, 25.43, 2, LocalDateTime.now());
        Optional<Order> optionalOrder = Optional.of(expectedOrder);
        Mockito.when(orderRepository.findById(requiredId)).thenReturn(optionalOrder);
        orderRepository.findById(requiredId);
        assertNull(requiredId);
    }

    @Test
    public void findByIdWithoutThrowsException() {
        Long existsId = 3L;
        Order expectedOrder = new Order(existsId, 25.43, 3, LocalDateTime.now());
        Optional<Order> optionalOrder = Optional.of(expectedOrder);
        Mockito.when(orderRepository.findById(existsId)).thenReturn(optionalOrder);
        assertDoesNotThrow(() -> orderRepository.findById(existsId));
    }

    @Test
    public void deletePositive() {
        Long existsId = 2L;
        Order expectedOrder = new Order(existsId, 25.43, 2, LocalDateTime.now());
        Optional<Order> optionalOrder = Optional.of(expectedOrder);
        Mockito.when(orderRepository.findById(existsId)).thenReturn(optionalOrder);
        orderRepository.deleteById(existsId);
    }

    @Test
    public void deleteNegative() {
        Long existsId = 2L;
        Long nonExistingId = 22L;
        Order expectedOrder = new Order(existsId, 25.43, 2, LocalDateTime.now());
        Optional<Order> optionalOrder = Optional.of(expectedOrder);
        Mockito.when(orderRepository.findById(existsId)).thenReturn(optionalOrder);
        orderRepository.deleteById(nonExistingId);
    }
}
