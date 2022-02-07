package com.epam.esm.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagRestController {
    public final TagServiceImpl tagService;
    private final ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> findAllTags() {
        List<Tag> listTag = tagService.findAll();
        return listTag
                .stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.findById(id);
        return modelMapper.map(tag.get(), TagDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody Tag tag) {
        Tag newTag = tagService.create(tag);
        return modelMapper.map(newTag, TagDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}
