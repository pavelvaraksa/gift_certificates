package com.epam.esm.dto;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.OrderDetails;
import com.epam.esm.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Order dto
 */
@Data
public class OrderDto {
    private Long id;

    private Double totalPrice;

    private LocalDateTime purchaseDate;

    private User user;

    private Set<GiftCertificateDto> certificate;
}
