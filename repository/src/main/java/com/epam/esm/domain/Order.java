package com.epam.esm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Order domain
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_table")
@DynamicUpdate
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", updatable = false)
    private Double totalPrice;

    @Column
    private Integer count;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @JsonIgnore
    @Cascade(CascadeType.PERSIST)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @Cascade(CascadeType.PERSIST)
    @ManyToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<GiftCertificate> certificate = new HashSet<>();

    @JsonIgnore
    @Cascade(CascadeType.PERSIST)
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<OrderDetails> orderDetails = new HashSet<>();

    public Order(Long id, Double totalPrice, Integer count, LocalDateTime purchaseDate) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.count = count;
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id)
                && Objects.equals(totalPrice, order.totalPrice)
                && Objects.equals(count, order.count)
                && Objects.equals(purchaseDate, order.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, count, purchaseDate);
    }
}
