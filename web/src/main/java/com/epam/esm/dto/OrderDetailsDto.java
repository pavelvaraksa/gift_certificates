package com.epam.esm.dto;

import lombok.Data;

/**
 * Order details dto
 */
@Data
public class OrderDetailsDto {
    private Double actualPrice;

    private GiftCertificateDto certificate;
}
