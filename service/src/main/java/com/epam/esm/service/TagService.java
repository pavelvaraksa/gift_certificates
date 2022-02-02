package com.epam.esm.service;

import com.epam.esm.domain.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();

    Tag findById(Long id);

    Tag create(Tag tag);

    void deleteById(Long id);
}
