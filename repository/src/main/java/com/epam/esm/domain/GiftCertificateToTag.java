package com.epam.esm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Gift certificate to tag domain
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gift_certificate_to_tag")
public class GiftCertificateToTag implements Serializable {
    @Id
    @Column(name = "gift_certificate_id")
    private Long giftCertificate;

    @Id
    @Column(name = "tag_id")
    private Long tag;

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
