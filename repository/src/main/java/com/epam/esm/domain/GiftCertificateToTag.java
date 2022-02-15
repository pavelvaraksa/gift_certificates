package com.epam.esm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Gift certificate to tag domain.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "gift_certificate_to_tag")
public class GiftCertificateToTag implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate giftCertificate;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateToTag that = (GiftCertificateToTag) o;
        return Objects.equals(giftCertificate, that.giftCertificate) && Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftCertificate, tag);
    }
}
