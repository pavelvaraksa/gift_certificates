package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.MessageException.CERTIFICATE_EXIST;
import static com.epam.esm.exception.MessageException.CERTIFICATE_NOT_FOUND;

/**
 * Gift certificate service implementation.
 */
@Log4j2
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;

    public GiftCertificateServiceImpl(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        LocalDateTime createdDateTime = LocalDateTime.now();
        giftCertificate.setCreateDate(createdDateTime);
        giftCertificate.setLastUpdateDate(createdDateTime);

        return repository.save(giftCertificate);
    }

    @Transactional
    @Override
    public GiftCertificate updateById(Long id, GiftCertificate giftCertificate) {
        Optional<GiftCertificate> giftCertificateById = repository.findById(id);

        if (giftCertificateById.isEmpty()) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        Optional<GiftCertificate> giftCertificateByName = repository.findByName(giftCertificate.getName());

        if (giftCertificateByName.isPresent()) {
            log.error("Gift certificate name " + giftCertificate.getName() + " already exist");
            throw new ServiceExistException(CERTIFICATE_EXIST);
        }

        if (giftCertificate.getName() == null) {
            giftCertificate.setName(giftCertificateById.get().getName());
        }

        if (giftCertificate.getDescription() == null) {
            giftCertificate.setDescription(giftCertificateById.get().getDescription());
        }

        if (giftCertificate.getPrice() == null) {
            giftCertificate.setPrice(giftCertificateById.get().getPrice());
        }

        if (giftCertificate.getDuration() == null) {
            giftCertificate.setDuration(giftCertificateById.get().getDuration());
        }

        GiftCertificateValidator.isGiftCertificateValid(giftCertificate);
        giftCertificate.setId(giftCertificateById.get().getId());
        giftCertificate.setCreateDate(giftCertificateById.get().getCreateDate());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

        repository.updateById(giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
                giftCertificate.getDuration(), giftCertificate.getId());

        log.info("Gift certificate with name " + giftCertificate.getName() + " updated");
        return giftCertificate;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<GiftCertificate> giftCertificateById = repository.findById(id);

        if (giftCertificateById.isEmpty()) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        log.info("Gift certificate with id " + id + " deleted");
        repository.deleteById(id);
    }
}
