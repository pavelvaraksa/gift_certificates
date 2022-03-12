package com.epam.esm.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagRestController {
    public final TagService tagService;
    public final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    /**
     * Find list of tags
     *
     * @return - page of tags or empty page
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> findAllTags(@PageableDefault(size = 5)
                                               @SortDefault(sort = "id", direction = Sort.Direction.DESC)
                                                       Pageable pageable, @RequestParam(value = "isDeleted",
            required = false, defaultValue = "false") boolean isDeleted) {
        List<Tag> tags = tagService.findAll(pageable, isDeleted);
        List<TagDto> items = new ArrayList<>();

        for (Tag tag : tags) {
            TagDto tagDto = modelMapper.map(tag, TagDto.class);
            tagDto.add(linkTo(methodOn(TagRestController.class).findTagById(tag.getId())).withSelfRel());
            items.add(tagDto);
        }

        return CollectionModel.of(items, linkTo(TagRestController.class).withRel("tags"));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> findAll(@PageableDefault(size = 5)
                                           @SortDefault(sort = "id", direction = Sort.Direction.DESC)
                                                   Pageable pageable) {
        Page<Tag> tags = tagService.findAllTags(pageable);
        List<TagDto> items = new ArrayList<>();

        for (Tag tag : tags) {
            TagDto tagDto = modelMapper.map(tag, TagDto.class);
            tagDto.add(linkTo(methodOn(TagRestController.class).findTagById(tag.getId())).withSelfRel());
            items.add(tagDto);
        }

        return CollectionModel.of(items, linkTo(TagRestController.class).withRel("tags"));
    }

    /**
     * Find tag by id
     *
     * @param id - tag id
     * @return - tag
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> findTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.findById(id);
        return EntityModel.of(modelMapper.map(tag.get(), TagDto.class),
                linkTo(TagRestController.class).withRel("tags"),
                linkTo(TagRestController.class).slash(tag.get().getId()).withSelfRel(),
                linkTo(TagRestController.class).slash("search?name=" + tag.get().getName()).withSelfRel());
    }

    /**
     * Find tag by name
     *
     * @param name - tag name
     * @return - tag
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> findTagByName(@RequestParam(value = "name", required = false) String name) {
        Optional<Tag> tag = tagService.findByName(name);
        return EntityModel.of(modelMapper.map(tag.get(), TagDto.class),
                linkTo(TagRestController.class).withRel("tags"),
                linkTo(TagRestController.class).slash(tag.get().getId()).withSelfRel(),
                linkTo(TagRestController.class).slash("search?name=" + name).withSelfRel());
    }

    /**
     * Create tag
     *
     * @param tag - tag
     * @return - tag
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> createTag(@RequestBody Tag tag) {
        Tag newTag = tagService.save(tag);
        return EntityModel.of(modelMapper.map(newTag, TagDto.class),
                linkTo(TagRestController.class).withRel("tags"),
                linkTo(TagRestController.class).slash(newTag.getId()).withSelfRel(),
                linkTo(TagRestController.class).slash("search?name=" + tag.getName()).withSelfRel());
    }

    /**
     * Delete tag by id
     *
     * @param id - tag id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
    }

    /**
     * Find most widely used tag
     *
     * @return - tag
     */
    @GetMapping("/widelyUsed")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> findMostWidelyUsed() {
        Optional<Tag> tag = tagService.findMostWidelyUsed();
        return EntityModel.of(modelMapper.map(tag.get(), TagDto.class),
                linkTo(TagRestController.class).withRel("tags"),
                linkTo(TagRestController.class).slash(tag.get().getId()).withSelfRel(),
                linkTo(TagRestController.class).slash("search?name=" + tag.get().getName()).withSelfRel());
    }
}

