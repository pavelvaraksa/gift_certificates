package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.StringValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class TagServiceImpl implements TagService {
    private final TagRepositoryImpl tagRepository;

    public TagServiceImpl(TagRepositoryImpl tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Long id) {
        Tag findById;


        findById = tagRepository.findById(id);
        if (findById == null) {
            String errorMessage = "Tag id cannot be null.";
            log.error(errorMessage);
            //throw new ServiceException(errorMessage);
        }

        log.info("Tag with id " + id + " exists.");
        return tagRepository.findById(id);
    }


    @Override
    public Tag create(Tag tag) {

        if (StringValidator.isTagValid(tag)) {
            //throw new ServiceException("Entered string for create tag was not passed.");
        }

        List<Tag> existingAllTags = tagRepository.findAll();

        for (Tag existingTags : existingAllTags) {
            boolean hasSameTag = existingTags.getName().equals(tag.getName());

            if (hasSameTag) {
                String errorMessage = "Tag with name " + tag.getName() + " already exists.";
                log.error(errorMessage);
               // throw new ServiceException(errorMessage);
            }
        }

        Tag newTag = tagRepository.create(tag);
        log.info("Tag with name " + tag.getName() + " saved.");
        return newTag;
    }

    @Override
    public void deleteById(Long id) {

        log.info("Tag with id " + id + " deleted.");
        tagRepository.deleteById(id);
    }
}