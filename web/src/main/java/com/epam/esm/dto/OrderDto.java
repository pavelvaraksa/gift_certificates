package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Order dto
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends RepresentationModel<OrderDto> {
    private Long id;

    private Double totalPrice;

    private Integer count;

    private LocalDateTime purchaseDate;

    private Set<OrderDetailsDto> orderDetails;
}
