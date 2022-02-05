package com.epam.esm.service;

import com.epam.esm.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> findAll();

    Optional<Tag> findById(Long id);

    Tag create(Tag tag);

    void deleteById(Long id);
}
