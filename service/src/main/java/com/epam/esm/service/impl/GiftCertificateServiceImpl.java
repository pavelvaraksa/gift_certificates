package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateRepository.findAll();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate.isEmpty()) {
            log.error("Gift certificate with id " + id + " was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findByName(name);

        if (giftCertificate.isEmpty()) {
            log.error("Gift certificate with name " + name + " was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        return giftCertificate;
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        GiftCertificateValidator.isGiftCertificateValid(giftCertificate);
        String giftCertificateName = giftCertificate.getName();
        Optional<GiftCertificate> giftCertificateByName = giftCertificateRepository.findByName(giftCertificateName);

        if (giftCertificateByName.isPresent()) {
            log.error("Gift certificate name " + giftCertificate.getName() + " already exist");
            throw new ServiceExistException(CERTIFICATE_EXIST);
        }

        LocalDateTime createdDateTime = LocalDateTime.now();
        giftCertificate.setCreateDate(createdDateTime);
        giftCertificate.setLastUpdateDate(createdDateTime);

        if (giftCertificate.getTags().isEmpty()) {
            return giftCertificateRepository.save(giftCertificate);
        }

        giftCertificate.getTags().forEach(tag -> {
            if (TagValidator.isTagValid(tag)) {
                String tagName = tag.getName();
                Optional<Tag> optionalTag = tagRepository.findByName(tagName);

                if (optionalTag.isPresent()) {
                    Tag existTag = optionalTag.get();
                    existTag.getGiftCertificateSet().add(giftCertificate);
                    return;
                }

                tag.getGiftCertificateSet().add(giftCertificate);
                tagRepository.save(tag);
            }
        });

        log.info("Gift certificate with name " + giftCertificate.getName() + " saved");
        return giftCertificateRepository.save(giftCertificate);
    }

    @Override
    public GiftCertificate updateById(Long id, GiftCertificate giftCertificate) {
        Optional<GiftCertificate> giftCertificateById = giftCertificateRepository.findById(id);

        if (giftCertificateById.isEmpty()) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        Optional<GiftCertificate> giftCertificateByName = giftCertificateRepository.findByName(giftCertificate.getName());

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

        giftCertificateRepository.updateById(giftCertificate);

        log.info("Gift certificate with name " + giftCertificate.getName() + " updated");
        return giftCertificate;
    }

    @Override
    public void deleteById(Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate.isEmpty()) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        log.info("Gift certificate with id " + id + " deleted");
        giftCertificateRepository.deleteById(id);
    }
}
