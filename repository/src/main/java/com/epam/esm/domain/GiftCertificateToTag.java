package com.epam.esm.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Gift certificate to tag domain.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
