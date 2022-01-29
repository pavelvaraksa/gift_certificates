package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> findAll();

    TagDto findById(Long id);

    TagDto create(TagDto tagDto);

    void deleteById(Long id);
}
