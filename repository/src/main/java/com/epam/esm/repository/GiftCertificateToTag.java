package com.epam.esm.repository;

import java.util.Optional;

public interface GiftCertificateToTag {
    boolean createLink(Long giftCertificateId, Long tagId);

    Optional<GiftCertificateToTag> findLink(Long giftCertificateId, Long tagId);
}
