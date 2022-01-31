package com.epam.esm.repository;


import com.epam.esm.domain.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<Long, GiftCertificate> {
    List<GiftCertificate> findByName(String name);
}
