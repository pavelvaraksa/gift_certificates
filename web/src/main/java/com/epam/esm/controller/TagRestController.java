package com.epam.esm.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.User;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceForbiddenException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import static com.epam.esm.exception.MessageException.USER_RESOURCE_FORBIDDEN;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagRestController {
    public final TagService tagService;
    public final TagRepository tagRepository;
    public final UserService userService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    /**
     * Find all tags
     *
     * @return - list of tags or empty list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> findAll(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return takeHateoasForUserWithAllTags(pageable);
    }

    /**
     * Find all tags
     *
     * @return - list of tags or empty list
     */
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> findAllForAdmin(@RequestParam(value = "deleted", required = false) boolean isActive,
                                                   @PageableDefault(sort = {"id"}) Pageable pageable) {
        return takeHateoasForAdminWithAllTags(isActive, pageable);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findByLogin(currentPrincipalName);
        String role = roleRepository.findRoleByUserId(user.get().getId());
        Optional<Tag> tag = tagService.findById(id);

        if (role.equals("ROLE_USER")) {
            return takeHateoasForUser(tag.get());
        } else if (role.equals("ROLE_ADMIN")) {
            return takeHateoasForAdmin(tag.get());
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findByLogin(currentPrincipalName);
        String role = roleRepository.findRoleByUserId(user.get().getId());
        Optional<Tag> tag = tagService.findByName(name);

        if (role.equals("ROLE_USER")) {
            return takeHateoasForUser(tag.get());
        } else if (role.equals("ROLE_ADMIN")) {
            return takeHateoasForAdmin(tag.get());
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
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
        return takeHateoasForAdmin(newTag);
    }

    /**
     * Find most widely used tag
     *
     * @return - tag
     */
    @GetMapping("/widelyUsed")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> findMostWidelyUsed() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findByLogin(currentPrincipalName);
        String role = roleRepository.findRoleByUserId(user.get().getId());
        Tag tag = tagService.findMostWidelyUsed();

        if (tag == null) {
            return EntityModel.of(modelMapper.map(Optional.ofNullable(tag), TagDto.class));
        }

        if (role.equals("ROLE_USER")) {
            return takeHateoasForUser(tag);
        } else if (role.equals("ROLE_ADMIN")) {
            return takeHateoasForAdmin(tag);
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
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
        return takeHateoasForAdmin(activatedTag);
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

    private CollectionModel<TagDto> takeHateoasForAdminWithAllTags(boolean isActive, Pageable pageable) {
        List<Tag> tags = tagService.findAllForAdmin(isActive, pageable);
        List<TagDto> items = new ArrayList<>();

        for (Tag tag : tags) {
            TagDto tagDto = modelMapper.map(tag, TagDto.class);
            tagDto.add(linkTo(methodOn(TagRestController.class).findTagById(tag.getId())).withRel("find by id"),
                    linkTo(methodOn(TagRestController.class).findTagByName(tag.getName())).withRel("find by name"),
                    linkTo(methodOn(TagRestController.class).deleteTag(tag.getId())).withRel("delete by id"));
            items.add(tagDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(TagRestController.class)
                .findAllForAdmin(isActive, pageable)).withRel("find all tags"));
    }

    private CollectionModel<TagDto> takeHateoasForUserWithAllTags(Pageable pageable) {
        List<Tag> tags = tagService.findAll(pageable);
        List<TagDto> items = new ArrayList<>();

        for (Tag tag : tags) {
            TagDto tagDto = modelMapper.map(tag, TagDto.class);
            tagDto.add(linkTo(methodOn(TagRestController.class).findTagById(tag.getId())).withRel("find by id"),
                    linkTo(methodOn(TagRestController.class).findTagByName(tag.getName())).withRel("find by name"));
            items.add(tagDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(TagRestController.class)
                .findAll(pageable)).withRel("find all tags"));
    }

    private EntityModel<TagDto> takeHateoasForAdmin(Tag tag) {
        return EntityModel.of(modelMapper.map(tag, TagDto.class),
                linkTo(methodOn(TagRestController.class).findTagById(tag.getId())).withRel("find by id"),
                linkTo(methodOn(TagRestController.class).findTagByName(tag.getName())).withRel("find by name"),
                linkTo(methodOn(TagRestController.class).deleteTag(tag.getId())).withRel("delete by id"));
    }

    private EntityModel<TagDto> takeHateoasForUser(Tag tag) {
        return EntityModel.of(modelMapper.map(tag, TagDto.class),
                linkTo(methodOn(TagRestController.class).findTagById(tag.getId())).withRel("find by id"),
                linkTo(methodOn(TagRestController.class).findTagByName(tag.getName())).withRel("find by name"));
    }
}



