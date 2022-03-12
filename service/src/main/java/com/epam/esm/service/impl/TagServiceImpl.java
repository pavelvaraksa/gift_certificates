package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import static com.epam.esm.exception.MessageException.TAG_EXIST;
import static com.epam.esm.exception.MessageException.TAG_NOT_FOUND;

/**
 * Tag service implementation
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final GiftCertificateService giftCertificateService;

    @Override
    public List<Tag> findAll(Pageable pageable, boolean isDeleted) {
        return tagRepository.findAll(pageable, isDeleted);
    }

    @Override
    public Page<Tag> findAllTags(Pageable pageable) {
        return tagRepository.findAllTags(pageable);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isEmpty()) {
            log.error("tag with id " + id + " was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        return tag;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);

        if (tag.isEmpty()) {
            log.error("tag with name " + name + " was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        return tag;
    }

    @Override
    public Tag save(Tag tag) {
        TagValidator.isTagValid(tag);
        String tagName = tag.getName();
        Optional<Tag> tagByName = tagRepository.findByName(tagName);

        if (tagByName.isPresent()) {
            log.error("Tag name " + tag.getName() + " already exist");
            throw new ServiceExistException(TAG_EXIST);
        }

        log.info("Tag with name " + tag.getName() + " saved");
        return tagRepository.save(tag);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isEmpty()) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        log.info("Tag with id " + id + " deleted");
        tagRepository.deleteById(id);
    }

    @Override
    public Optional<Tag> findMostWidelyUsed() {
        List<Long> certificates = new ArrayList<>();
        List<Long> allCertificates;
        List<Long> tags = new ArrayList<>();
        List<Long> allTags;

        List<Long> listUserId = userService.findAllForWidelyUsedTag();
        Long orderId = orderService.findIdWithHighestCost(listUserId);
        allCertificates = giftCertificateService.findAllIdByOrderId(orderId);
        certificates.addAll(allCertificates);

        for (Long certificateId : certificates) {
            allTags = tagRepository.findByCertificate(certificateId);
            tags.addAll(allTags);
        }

        if (tags.size() == 0) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        Long widelyUsedTagId = tags.stream()
                .reduce(BinaryOperator.maxBy(Comparator.comparingInt(tag -> Collections.frequency(tags, tag)))).orElseThrow();
        return tagRepository.findById(widelyUsedTagId);
    }
}

