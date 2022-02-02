package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.epam.esm.exception.MessageException.TAG_NOT_FOUND;

import java.util.List;

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
    public Tag findById(Long id) {
        Tag tagById = tagRepository.findById(id);

        if (tagById == null) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        return tagById;
    }


    @Override
    public Tag create(Tag tag) {
        TagValidator.isTagValid(tag);

        log.info("Tag with name " + tag.getName() + " saved");
        return tag;
    }

    @Override
    public void deleteById(Long id) {
        Tag tagById = tagRepository.findById(id);

        if (tagById == null) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        log.info("Tag with id " + id + " deleted");
        tagRepository.deleteById(id);
    }
}