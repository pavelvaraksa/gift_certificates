package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.repository.GiftCertificateToTag;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.GiftCertificateToTagImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.exception.MessageException.*;

@Log4j2
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepositoryImpl giftCertificateRepository;
    private final GiftCertificateToTagImpl giftCertificateToTag;
    private final TagRepositoryImpl tagRepository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepositoryImpl giftCertificateRepository,
                                      GiftCertificateToTagImpl giftCertificateToTag,
                                      TagRepositoryImpl tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateToTag = giftCertificateToTag;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
        findAndSetTags(giftCertificates);

        return giftCertificates;
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate == null) {
            log.error("Gift certificate with id " + id + " was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        Set<Tag> tagList = tagRepository.findByGiftCertificateId(id);
        giftCertificate.setTags(tagList);

        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findByPartName(String partName) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findByPartName(partName);

        if (giftCertificate.isEmpty()) {
            log.error("Gift certificate by part of name " + giftCertificate + " not found");
            throw new ServiceExistException(CERTIFICATE_NOT_FOUND);
        }

        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findByPartDescription(String partDescription) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findByPartDescription(partDescription);

        if (giftCertificate.isEmpty()) {
            log.error("Gift certificate by part of description " + giftCertificate + " not found");
            throw new ServiceExistException(CERTIFICATE_NOT_FOUND);
        }

        return giftCertificate;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        GiftCertificateValidator.isGiftCertificateValid(giftCertificate);
        String giftCertificateName = giftCertificate.getName();
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findByName(giftCertificateName);

        if (optionalGiftCertificate.isPresent()) {
            log.error("Gift certificate name " + giftCertificate.getName() + " already exist");
            throw new ServiceExistException(CERTIFICATE_EXIST);
        }

        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        GiftCertificate newGiftCertificate = giftCertificateRepository.create(giftCertificate);

        if (giftCertificate.getTags() == null) {
            return newGiftCertificate;
        }

        giftCertificate.getTags().forEach(tag -> {
            if (TagValidator.isTagValid(tag)) {
                String tagName = tag.getName();
                Optional<Tag> optionalTag = tagRepository.findByName(tagName);

                Long giftCertificateId = newGiftCertificate.getId();
                Long tagId;

                if (optionalTag.isPresent()) {
                    Tag existTag = optionalTag.get();
                    tagId = existTag.getId();
                    Optional<GiftCertificateToTag> link = giftCertificateToTag.findLink(giftCertificateId, tagId);

                    if (!link.isPresent()) {
                        giftCertificateToTag.createLink(giftCertificateId, tagId);
                    }
                } else {
                    Tag createdTag = tagRepository.create(tag);
                    tagId = createdTag.getId();
                    giftCertificateToTag.createLink(giftCertificateId, tagId);
                }
            }
        });
        return giftCertificate;
    }

    @Override
    public GiftCertificate updateById(Long id, GiftCertificate giftCertificate) {
        GiftCertificate giftCertificateById = giftCertificateRepository.findById(id);

        if (giftCertificateById == null) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        Optional<GiftCertificate> GiftCertificateByName = giftCertificateRepository.findByName(giftCertificate.getName());

        if (GiftCertificateByName.isPresent()) {
            log.error("Gift certificate name " + giftCertificate.getName() + " already exist");
            throw new ServiceExistException(CERTIFICATE_EXIST);
        }

        if (giftCertificate.getName() == null) {
            giftCertificate.setName(giftCertificateById.getName());
        }

        if (giftCertificate.getDescription() == null) {
            giftCertificate.setDescription(giftCertificateById.getDescription());
        }

        if (giftCertificate.getPrice() == null) {
            giftCertificate.setPrice(giftCertificateById.getPrice());
        }

        if (giftCertificate.getDuration() == null) {
            giftCertificate.setDuration(giftCertificateById.getDuration());
        }

        GiftCertificateValidator.isGiftCertificateValid(giftCertificate);

        GiftCertificate updatedGiftCertificate = giftCertificateRepository.updateById(id, giftCertificate);
        findAndSetTags(updatedGiftCertificate);

        log.info("Gift certificate with name " + giftCertificate.getName() + " updated");
        return updatedGiftCertificate;
    }

    @Override
    public void deleteById(Long id) {
        GiftCertificate giftCertificateById = giftCertificateRepository.findById(id);

        if (giftCertificateById == null) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        log.info("Gift certificate with id " + id + " deleted");
        tagRepository.deleteAllTagsByGiftCertificateId(id);
        giftCertificateRepository.deleteById(id);
    }

    private void findAndSetTags(List<GiftCertificate> giftCertificates) {
        giftCertificates.forEach(giftCertificate -> {
            long id = giftCertificate.getId();
            Set<Tag> tags = tagRepository.findByGiftCertificateId(id);
            giftCertificate.setTags(tags);
        });
    }

    private void findAndSetTags(GiftCertificate giftCertificate) {
        long id = giftCertificate.getId();
        Set<Tag> tags = tagRepository.findByGiftCertificateId(id);
        giftCertificate.setTags(tags);
    }
}
