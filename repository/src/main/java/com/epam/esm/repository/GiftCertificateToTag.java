package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateToTag {
    boolean createLink(Long giftCertificateId, Long tagId);

    Optional<GiftCertificateToTag> findLink(Long giftCertificateId, Long tagId);

    List<GiftCertificateToTag> findAllByGiftCertificateId(Long id);

    boolean deleteLink(Long giftCertificateId, Long tagId);
}
