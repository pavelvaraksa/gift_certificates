package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAllTags();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isEmpty()) {
            log.error("Tag with id " + id + " was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        if (tag.get().isActive()) {
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        return tag;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);

        if (tag.isEmpty()) {
            log.error("Tag with name " + name + " was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        if (tag.get().isActive()) {
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        return tag;
    }

    @Transactional
    @Override
    public Tag save(Tag tag) {
        TagValidator.isTagValid(tag);
        String tagName = tag.getName();
        Optional<Tag> tagByName = tagRepository.findByName(tagName);

        if (tagByName.isPresent()) {
            log.error("Tag name " + tagByName + " already exists");
            throw new ServiceExistException(TAG_EXIST);
        }

        log.info("Tag with name " + tag.getName() + " saved");
        return tagRepository.save(tag);
    }

    @Override
    public Tag findMostWidelyUsed() {
        return tagRepository.findMostWidelyUsed();
    }

    @Transactional
    @Override
    public Tag activateById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isEmpty()) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        if (tag.get().isActive()) {
            tagRepository.activateById(id);
            return tag.get();
        } else {
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public Tag deleteById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isEmpty()) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        if (tag.get().isActive()) {
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        log.info("Tag with id " + id + " deleted");
        tagRepository.deleteById(id);
        return tag.get();
    }
}

