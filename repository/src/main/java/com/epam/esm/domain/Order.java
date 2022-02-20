package com.epam.esm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Order domain
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "order_table")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal price;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "gift_certificate_id")
    private Long giftCertificateId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(price, order.price) && Objects.equals(purchaseDate, order.purchaseDate) && Objects.equals(userId, order.userId) && Objects.equals(giftCertificateId, order.giftCertificateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, purchaseDate, userId, giftCertificateId);
    }
}
