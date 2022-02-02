package com.epam.esm.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagRestController {
    public final TagServiceImpl tagService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<TagDto>> findAllTags() {
        List<Tag> listTag = tagService.findAll();
        return ResponseEntity.ok(listTag
                .stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TagDto> findTagById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        return ResponseEntity.ok(modelMapper.map(tag, TagDto.class));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TagDto> createTag(@RequestBody Tag tag) {
        Tag newTag = tagService.create(tag);
        return ResponseEntity.ok(modelMapper.map(newTag, TagDto.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
