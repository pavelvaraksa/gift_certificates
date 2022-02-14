package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.repository.GiftCertificateToTagRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.GiftCertificateToTagRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortType;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.exception.MessageException.TAG_NOT_FOUND;
import static com.epam.esm.exception.MessageException.CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.MessageException.CERTIFICATE_EXIST;

/**
 * Gift certificate service implementation.
 */
@Log4j2
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepositoryImpl giftCertificateRepository;
    private final GiftCertificateToTagRepositoryImpl giftCertificateToTag;
    private final TagRepositoryImpl tagRepository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepositoryImpl giftCertificateRepository,
                                      GiftCertificateToTagRepositoryImpl giftCertificateToTag,
                                      TagRepositoryImpl tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateToTag = giftCertificateToTag;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
        //findSetTagsForEach(giftCertificates);

        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findAllSorted(Set<ColumnName> columnNames, SortType sortType) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAllSorted(columnNames, sortType);
        //findSetTagsForEach(giftCertificates);

        return giftCertificates;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate == null) {
            log.error("Gift certificate with id " + id + " was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        Set<Tag> tagList = tagRepository.findByGiftCertificateId(id);
        //giftCertificate.get().setTags(tagList);

        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public List<GiftCertificate> search(String partname,String partDescription) {
        List<GiftCertificate> giftCertificates;
        List<GiftCertificate> findByPartName = giftCertificateRepository.findByPartName(partname);
        List<GiftCertificate> findByPartDescription = giftCertificateRepository.findByPartDescription(partDescription);

        if (partname == null) {
            giftCertificates = findByPartDescription;
        } else {
            giftCertificates = findByPartName;
            giftCertificates.addAll(findByPartDescription);
            //findSetTagsForEach(giftCertificates);
        }
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findByName(tagName);

        if (optionalTag.isEmpty()) {
            log.error("Tag with name " + tagName + " was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        Long tagId = optionalTag.get().getId();
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findByTagId(tagId);

        if (!giftCertificates.isEmpty()) {
            //findSetTagsForEach(giftCertificates);
        } else {
            log.error("Gift certificate by tag name " + tagName + " was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        return giftCertificates;
    }

    @Transactional()
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

//        if (giftCertificate.getTags() == null) {
//            return newGiftCertificate;
//        }

//        giftCertificate.getTags().forEach(tag -> {
//            if (TagValidator.isTagValid(tag)) {
//                String tagName = tag.getName();
//                Optional<Tag> optionalTag = tagRepository.findByName(tagName);
//
//                Long giftCertificateId = newGiftCertificate.getId();
//                Long tagId;
//
//                if (optionalTag.isPresent()) {
//                    Tag existTag = optionalTag.get();
//                    tagId = existTag.getId();
//                    Optional<GiftCertificateToTagRepository> link = giftCertificateToTag.findLink(giftCertificateId, tagId);
//
//                    if (!link.isPresent()) {
//                        giftCertificateToTag.createLink(giftCertificateId, tagId);
//                    }
//                } else {
//                    Tag createdTag = tagRepository.create(tag);
//                    tagId = createdTag.getId();
//                    giftCertificateToTag.createLink(giftCertificateId, tagId);
//                }
//            }
//        });
//        return giftCertificate;
        return giftCertificate;
    }

    @Transactional
    @Override
    public GiftCertificate updateById(Long id, GiftCertificate giftCertificate) {
        GiftCertificate giftCertificateById = giftCertificateRepository.findById(id);

        if (giftCertificateById == null) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        Optional<GiftCertificate> giftCertificateByName = giftCertificateRepository.findByName(giftCertificate.getName());

        if (giftCertificateByName.isPresent()) {
            log.error("Gift certificate name " + giftCertificate.getName() + " already exist");
            throw new ServiceExistException(CERTIFICATE_EXIST);
        }

        if (giftCertificate.getName() == null) {
            giftCertificate.setName(giftCertificateById.getName());
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
        giftCertificate.setId(giftCertificateById.getId());
        giftCertificate.setCreateDate(giftCertificateById.getCreateDate());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

        GiftCertificate updatedGiftCertificate = giftCertificateRepository.updateById(giftCertificate);
        //findSetTag(updatedGiftCertificate);

        log.info("Gift certificate with name " + giftCertificate.getName() + " updated");
        return updatedGiftCertificate;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        GiftCertificate giftCertificateById = giftCertificateRepository.findById(id);

        if (giftCertificateById == null) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        log.info("Gift certificate with id " + id + " deleted");
        //tagRepository.deleteAllTagsByGiftCertificateId(id);
        giftCertificateRepository.deleteById(id);
        return true;
    }

//    private void findSetTagsForEach(List<GiftCertificate> giftCertificates) {
//        giftCertificates.forEach(giftCertificate -> {
//            long id = giftCertificate.getId();
//            Set<Tag> tags = tagRepository.findByGiftCertificateId(id);
//            giftCertificate.setTags(tags);
//        });
//    }
//
//    private void findSetTag(GiftCertificate giftCertificate) {
//        long id = giftCertificate.getId();
//        Set<Tag> tags = tagRepository.findByGiftCertificateId(id);
//        giftCertificate.setTags(tags);
//    }
}
