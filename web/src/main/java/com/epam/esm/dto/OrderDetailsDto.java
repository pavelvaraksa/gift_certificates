package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * Order details dto
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OrderDetailsDto extends RepresentationModel<OrderDetailsDto> {
    private Double actualPrice;

    private GiftCertificateDto certificate;
}
