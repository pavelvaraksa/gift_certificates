package com.epam.esm.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
     * Find all tags
     *
     * @return - list of tags or empty list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> findAll() {
        List<Tag> tags = tagService.findAll();
        List<TagDto> items = new ArrayList<>();

        for (Tag tag : tags) {
            TagDto tagDto = modelMapper.map(tag, TagDto.class);
            tagDto.add(linkTo(methodOn(TagRestController.class).findTagById(tag.getId())).withRel("find by id"),
                    linkTo(methodOn(TagRestController.class).findTagByName(tag.getName())).withRel("find by name"));
            items.add(tagDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(TagRestController.class)
                .findAll()).withRel("find all tags"));
    }

    /**
     * Find all tags
     *
     * @return - list of tags or empty list
     */
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> findAllForAdmin(@RequestParam(value = "deleted", required = false) boolean isActive) {
        List<Tag> tags = tagService.findAllForAdmin(isActive);
        List<TagDto> items = new ArrayList<>();

        for (Tag tag : tags) {
            TagDto tagDto = modelMapper.map(tag, TagDto.class);
            tagDto.add(linkTo(methodOn(TagRestController.class).findTagById(tag.getId())).withRel("find by id"),
                    linkTo(methodOn(TagRestController.class).findTagByName(tag.getName())).withRel("find by name"),
                    linkTo(methodOn(TagRestController.class).deleteTag(tag.getId())).withRel("delete by id"));
            items.add(tagDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(TagRestController.class)
                .findAllForAdmin(isActive)).withRel("find all tags"));
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
                linkTo(methodOn(TagRestController.class).findTagById(id)).withRel("find by id"),
                linkTo(methodOn(TagRestController.class).findTagByName(tag.get().getName())).withRel("find by name"),
                linkTo(methodOn(TagRestController.class).deleteTag(id)).withRel("delete by id"));
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
                linkTo(methodOn(TagRestController.class).findTagById(tag.get().getId())).withRel("find by id"),
                linkTo(methodOn(TagRestController.class).findTagByName(tag.get().getName())).withRel("find by name"),
                linkTo(methodOn(TagRestController.class).deleteTag(tag.get().getId())).withRel("delete by id"));
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
                linkTo(methodOn(TagRestController.class).findTagById(newTag.getId())).withRel("find by id"),
                linkTo(methodOn(TagRestController.class).findTagByName(newTag.getName())).withRel("find by name"),
                linkTo(methodOn(TagRestController.class).deleteTag(newTag.getId())).withRel("delete by id"));
    }

    /**
     * Find most widely used tag
     *
     * @return - tag
     */
    @GetMapping("/widelyUsed")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> findMostWidelyUsed() {
        Tag tag = tagService.findMostWidelyUsed();

        if (tag == null) {
            return EntityModel.of(modelMapper.map(Optional.ofNullable(tag), TagDto.class));
        }

        return EntityModel.of(modelMapper.map(tag, TagDto.class),
                linkTo(methodOn(TagRestController.class).findTagById(tag.getId())).withRel("find by id"),
                linkTo(methodOn(TagRestController.class).findTagByName(tag.getName())).withRel("find by name"),
                linkTo(methodOn(TagRestController.class).deleteTag(tag.getId())).withRel("delete by id"));
    }

    /**
     * Activate tag by id
     *
     * @param id - tag id
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> activateGiftCertificate(@PathVariable Long id) {
        Tag activatedTag = tagService.activateById(id);
        return EntityModel.of(modelMapper.map(activatedTag, TagDto.class),
                linkTo(methodOn(TagRestController.class).findTagById(id)).withRel("find by id"),
                linkTo(methodOn(TagRestController.class).findTagByName(activatedTag.getName())).withRel("find by name"),
                linkTo(methodOn(TagRestController.class).deleteTag(id)).withRel("delete by id"));
    }

    /**
     * Delete tag by id
     *
     * @param id - tag id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> deleteTag(@PathVariable Long id) {
        Tag deletedTag = tagService.deleteById(id);
        return EntityModel.of(modelMapper.map(deletedTag, TagDto.class));
    }
}



