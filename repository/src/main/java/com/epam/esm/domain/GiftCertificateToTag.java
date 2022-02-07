package com.epam.esm.domain;

import lombok.Data;

/**
 * Gift certificate to tag domain.
 */
@Data
public class GiftCertificateToTag {
    private Long giftCertificateId;
    private Long tagId;
}
