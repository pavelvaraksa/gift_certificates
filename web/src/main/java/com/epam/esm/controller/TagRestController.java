package com.epam.esm.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
     * Find tag by id.
     *
     * @param id - tag id.
     * @return - tag.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.findById(id);
        return modelMapper.map(tag.get(), TagDto.class);
    }

    /**
     * Find tag by name.
     *
     * @param name - tag name.
     * @return - tag.
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findTagByName(@RequestParam(value = "name", required = false) String name) {
        Optional<Tag> tag = tagService.findByName(name);
        return modelMapper.map(tag.get(), TagDto.class);
    }

    /**
     * Create tag.
     *
     * @param tag - tag.
     * @return - tag.
     */
    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody Tag tag) {
        Tag newTag = tagService.save(tag);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tag.getId())
                .toUri();

        return ResponseEntity.created(location).body(modelMapper.map(newTag, TagDto.class));
    }

    /**
     * Delete tag by id.
     *
     * @param id - tag id.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}

