package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.StringValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.epam.esm.exception.MessageException.TAG_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepositoryImpl tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TagDto> findAll() {
        List<Tag> listTag = tagRepository.findAll();
        return listTag
                .stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(Long id) {
        Tag tagById = tagRepository.findById(id);

        if (tagById == null) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }
        return modelMapper.map(tagById, TagDto.class);
    }


    @Override
    public TagDto create(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        StringValidator.isTagValid(tagDto);

        log.info("Tag with name " + tag.getName() + " saved");
        return modelMapper.map(tagRepository.create(tag), TagDto.class);
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