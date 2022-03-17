package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.GiftCertificateToTag;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateToTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ColumnCertificateName;
import com.epam.esm.util.SortType;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.exception.MessageException.CERTIFICATE_EXIST;
import static com.epam.esm.exception.MessageException.CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.MessageException.TAG_NOT_FOUND;

/**
 * Gift certificate service implementation
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateToTagRepository certificateTagLink;
    private final TagRepository tagRepository;

    @Override
    public List<GiftCertificate> findAll(Pageable pageable, Set<ColumnCertificateName> column, SortType sort, boolean isDeleted) {
        return giftCertificateRepository.findAll(pageable, column, sort, isDeleted);
    }

    public List<Long> findAllIdByOrderId(Long id) {
        List<Long> certificates = giftCertificateRepository.findAllByOrderId(id);
        List<Long> tags = new ArrayList<>();
        List<Long> allTags;

        for (Long certificateId : certificates) {
            allTags = tagRepository.findAllIdByCertificateId(certificateId);

            Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(certificateId);

            if (!giftCertificate.get().getTag().isEmpty()) {
                tags.addAll(allTags);
            }
        }

        return certificates;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate.isEmpty()) {
            log.error("Gift certificate with id " + id + " was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        if (giftCertificate.get().isActive()) {
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

        if (giftCertificate.get().isActive()) {
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findByTagName(List<String> tagName) {
        Optional<Tag> optionalTag;
        List<GiftCertificate> foundCertificates = new ArrayList<>();
        List<GiftCertificate> giftCertificates = new ArrayList<>();

        for (String name : tagName) {
            optionalTag = tagRepository.findByName(name);

            if (!optionalTag.isPresent()) {
                log.error("Tag with name " + tagName + " was not found");
                throw new ServiceNotFoundException(TAG_NOT_FOUND);
            }
        }

        for (String name : tagName) {
            optionalTag = tagRepository.findByName(name);
            Long tagId = optionalTag.get().getId();
            foundCertificates = giftCertificateRepository.findByTagId(tagId);

            if (!giftCertificates.equals(foundCertificates)) {
                giftCertificates.addAll(foundCertificates);
            }
        }

        for (GiftCertificate name : foundCertificates) {
            Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findByName(name.getName());

            if (giftCertificate.get().isActive()) {
                log.error("Gift certificate was not found");
                throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
            }
        }

        return giftCertificates;
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

        giftCertificate.setCurrentPrice(giftCertificate.getCurrentPrice());
        LocalDateTime createdDateTime = LocalDateTime.now();
        giftCertificate.setCreateDate(createdDateTime);
        giftCertificate.setLastUpdateDate(createdDateTime);
        GiftCertificate newGiftCertificate = giftCertificateRepository.save(giftCertificate);

        if (giftCertificate.getTag().isEmpty()) {
            return newGiftCertificate;
        }

        giftCertificate.getTag().forEach(tag -> {
            if (TagValidator.isTagValid(tag)) {
                String tagName = tag.getName();
                Optional<Tag> optionalTag2 = tagRepository.findByName(tagName);

                if (optionalTag2.isPresent()) {
                    Tag existTag = optionalTag2.get();
                    boolean isExistLink = certificateTagLink.isExistLink(newGiftCertificate.getId(), existTag.getId());

                    if (!isExistLink) {
                        GiftCertificateToTag certificateToTag = new GiftCertificateToTag(newGiftCertificate.getId(), existTag.getId());
                        certificateTagLink.save(certificateToTag);
                    }
                } else {
                    tagRepository.save(tag);
                    GiftCertificateToTag certificateToTag = new GiftCertificateToTag(newGiftCertificate.getId(), tag.getId());
                    certificateTagLink.save(certificateToTag);
                }
            }
        });

        return giftCertificate;
    }

    @Override
    public GiftCertificate updateById(Long id, GiftCertificate giftCertificate) {
        Optional<GiftCertificate> giftCertificateById = giftCertificateRepository.findById(id);

        if (giftCertificateById.isEmpty()) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        if (giftCertificateById.get().isActive()) {
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

        if (giftCertificate.getCurrentPrice() == null) {
            giftCertificate.setCurrentPrice(giftCertificateById.get().getCurrentPrice());
        }

        if (giftCertificate.getDuration() == null) {
            giftCertificate.setDuration(giftCertificateById.get().getDuration());
        }

        GiftCertificateValidator.isGiftCertificateValid(giftCertificate);
        giftCertificate.setId(giftCertificateById.get().getId());
        giftCertificate.setCreateDate(giftCertificateById.get().getCreateDate());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        List<Tag> existTags = tagRepository.findAllByCertificateId(giftCertificate.getId());
        giftCertificate.setTag(existTags);

        log.info("Gift certificate with name " + giftCertificate.getName() + " updated");
        return giftCertificateRepository.updateById(giftCertificate);
    }

    @Override
    public GiftCertificate activateById(Long id, boolean isCommand) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate.isEmpty()) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        if (giftCertificate.get().isActive()) {
            return giftCertificateRepository.activateById(id);
        } else {
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }
    }

    @Override
    public GiftCertificate deleteById(Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate.isEmpty()) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        if (giftCertificate.get().isActive()) {
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        log.info("Gift certificate with id " + id + " deleted");
        giftCertificateRepository.deleteById(id);
        return giftCertificate.get();
    }
}