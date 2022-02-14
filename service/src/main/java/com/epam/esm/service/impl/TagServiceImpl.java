package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.epam.esm.exception.MessageException.TAG_EXIST;
import static com.epam.esm.exception.MessageException.TAG_NOT_FOUND;

import java.util.List;
import java.util.Optional;

/**
 * Tag service implementation.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepositoryImpl tagRepository;

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Tag tag = tagRepository.findById(id);

        if (tag == null) {
            log.error("tag with id " + id + " was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        return Optional.ofNullable(tag);
    }

    @Override
    public Tag create(Tag tag) {
        TagValidator.isTagValid(tag);
        String tagName = tag.getName();
        Optional<Tag> optionalTag = tagRepository.findByName(tagName);

        if (optionalTag.isPresent()) {
            log.error("Tag name " + tag.getName() + " already exist");
            throw new ServiceExistException(TAG_EXIST);
        }

        log.info("Tag with name " + tag.getName() + " saved");
        return tagRepository.create(tag);
    }

    @Override
    public boolean deleteById(Long id) {
        Tag tagById = tagRepository.findById(id);

        if (tagById == null) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        log.info("Tag with id " + id + " deleted");
        return tagRepository.deleteById(id);
    }
}