package com.epam.esm.dto;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order dto
 */
@Data
public class OrderDto {
    private Long id;

    private BigDecimal price;

    private LocalDateTime purchaseDate;

    private User user;

    private GiftCertificate giftCertificate;
}
