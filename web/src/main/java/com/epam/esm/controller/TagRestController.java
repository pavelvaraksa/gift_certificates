package com.epam.esm.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagRestController {
    public final TagServiceImpl tagService;
    private final ModelMapper modelMapper;

    /**
     * Find list of tags.
     *
     * @return - list of tags or empty list.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> findAllTags() {
        List<Tag> listTag = tagService.findAll();
        return listTag
                .stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Find tag by ID.
     *
     * @param id - tag ID.
     * @return - tag.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.findById(id);
        return modelMapper.map(tag.get(), TagDto.class);
    }

    /**
     * Create tag.
     *
     * @param tag - tag.
     * @return - tag.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody Tag tag) {
        Tag newTag = tagService.create(tag);
        return modelMapper.map(newTag, TagDto.class);
    }

    /**
     * Delete tag by ID.
     *
     * @param id - tag ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}
