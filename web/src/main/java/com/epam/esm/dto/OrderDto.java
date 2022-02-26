package com.epam.esm.dto;

import com.epam.esm.domain.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Order dto
 */
@Data
public class OrderDto {
    private Long id;

    private Double totalPrice;

    private Integer count;

    private LocalDateTime purchaseDate;

    private User user;

    private Set<OrderDetailsDto> orderDetails;
}
