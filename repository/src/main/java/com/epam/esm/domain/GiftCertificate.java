package com.epam.esm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Gift certificate domain.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate implements Serializable {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @Column
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
}
