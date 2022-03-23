package com.epam.esm.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class GiftCertificateToTagId implements Serializable {
    private Long giftCertificate;

    private Long tag;
}
