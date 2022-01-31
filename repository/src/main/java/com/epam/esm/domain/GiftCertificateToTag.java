package com.epam.esm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GiftCertificateToTag {
    private Long giftCertificateId;
    private Long tagId;
}
