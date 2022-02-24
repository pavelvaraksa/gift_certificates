package com.epam.esm.dto;

import lombok.Data;

@Data
public class OrderDetailsDto {
    private OrderDto order;

    private GiftCertificateDto certificate;

    private Double actualPrice;
}
