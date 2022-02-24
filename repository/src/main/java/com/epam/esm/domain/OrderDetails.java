package com.epam.esm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Order details domain
 */
@Getter
@Setter
@Entity
@Table(name = "order_details")
@DynamicUpdate
public class OrderDetails implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate certificate;

    @Column(name = "actual_price", updatable = false)
    private Double actualPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return Objects.equals(order, that.order)
                && Objects.equals(certificate, that.certificate)
                && Objects.equals(actualPrice, that.actualPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, certificate, actualPrice);
    }
}
