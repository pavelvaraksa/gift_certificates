package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ColumnTagName;
import com.epam.esm.util.SortType;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<Tag> findAll(Pageable pageable, Set<ColumnTagName> column, SortType sort, boolean isDeleted) {
        return tagRepository.findAll(pageable, column, sort, isDeleted);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isEmpty()) {
            log.error("tag with id " + id + " was not found");
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
            log.error("tag with name " + name + " was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        if (tag.get().isActive()) {
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
    public Tag findMostWidelyUsed() {
        return tagRepository.findMostWidelyUsed();
    }

    @Override
    public Tag activateById(Long id, boolean isCommand) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isEmpty()) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        if (tag.get().isActive()) {
            return tagRepository.activateById(id);
        } else {
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }
    }

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

