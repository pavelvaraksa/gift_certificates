package com.epam.esm.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Gift certificate dto
 */
@Data
public class GiftCertificateDto {
    private Long id;

    private String name;

    private String description;

    private Double currentPrice;

    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private Set<TagDto> tag;
}
