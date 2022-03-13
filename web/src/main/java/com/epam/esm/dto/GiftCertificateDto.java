package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Gift certificate dto
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    private Long id;

    private String name;

    private String description;

    private Double currentPrice;

    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private Set<TagDto> tag;
}
