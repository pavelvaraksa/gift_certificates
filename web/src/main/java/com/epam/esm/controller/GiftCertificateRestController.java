package com.epam.esm.controller;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.User;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ServiceForbiddenException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.GiftCertificateService;
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
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateRestController {
    public final GiftCertificateService giftCertificateService;
    public final UserService userService;
    public final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public final TagService tagService;
    private final ModelMapper modelMapper;

    /**
     * Find all certificates
     *
     * @return - list of certificates or empty list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GiftCertificateDto> findAllCertificates(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return takeHateoasForUserWithAllCertificates(pageable);
    }

    /**
     * Find all certificates
     *
     * @return - list of certificates or empty list
     */
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GiftCertificateDto> findAllCertificatesForAdmin(@RequestParam(value = "deleted", required = false) boolean isActive,
                                                                           @PageableDefault(sort = {"id"}) Pageable pageable) {
        return takeHateoasForAdminWithAllCertificates(isActive, pageable);
    }

    /**
     * Find gift certificate by id
     *
     * @param id - gift certificate id
     * @return - gift certificate
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> findGiftCertificateById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userRepository.findByLogin(currentPrincipalName);
        Optional<GiftCertificate> giftCertificate = giftCertificateService.findById(id);

        if (user.isEmpty()) {
            return takeHateoasForUser(giftCertificate.get());
        }

        String role = roleRepository.findRoleByUserId(user.get().getId());

        if (role.equals("ROLE_USER")) {
            return takeHateoasForUser(giftCertificate.get());
        } else if (role.equals("ROLE_ADMIN")) {
            return takeHateoasForAdmin(giftCertificate.get());
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
    }

    /**
     * Find gift certificate by name
     *
     * @param name - gift certificate name
     * @return - gift certificate
     */
    @GetMapping("/search/name")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> findGiftCertificateByName(@RequestParam String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userRepository.findByLogin(currentPrincipalName);
        Optional<GiftCertificate> giftCertificate = giftCertificateService.findByName(name);

        if (user.isEmpty()) {
            return takeHateoasForUser(giftCertificate.get());
        }

        String role = roleRepository.findRoleByUserId(user.get().getId());

        if (role.equals("ROLE_USER")) {
            return takeHateoasForUser(giftCertificate.get());
        } else if (role.equals("ROLE_ADMIN")) {
            return takeHateoasForAdmin(giftCertificate.get());
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
    }

    /**
     * Find gift certificate by tag name
     *
     * @param tagName - tag name
     * @return - list of gift certificates
     */
    @GetMapping("/search/tagName")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GiftCertificateDto> findGiftCertificateByTagName(@RequestParam List<String> tagName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userRepository.findByLogin(currentPrincipalName);

        if (user.isEmpty()) {
            return takeHateoasForUserByTagName(tagName);
        }

        String role = roleRepository.findRoleByUserId(user.get().getId());

        if (role.equals("ROLE_USER")) {
            return takeHateoasForUserByTagName(tagName);
        } else if (role.equals("ROLE_ADMIN")) {
            return takeHateoasForAdminByTagName(tagName);
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
    }

    /**
     * Create gift certificate
     *
     * @param giftCertificate - gift certificate
     * @return - gift certificate
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<GiftCertificateDto> saveGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate newGiftCertificate = giftCertificateService.save(giftCertificate);
        return takeHateoasForAdmin(newGiftCertificate);
    }

    /**
     * Update gift certificate by id
     *
     * @param id              - gift certificate id
     * @param giftCertificate - gift certificate
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> updateGiftCertificate(@PathVariable Long id,
                                                                 @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate updatedGiftCertificate = giftCertificateService.updateById(id, giftCertificate);
        return takeHateoasForAdmin(updatedGiftCertificate);
    }

    /**
     * Activate gift certificate by id
     *
     * @param id - gift certificate id
     */
    @PatchMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> activateGiftCertificate(@PathVariable Long id) {
        GiftCertificate activatedGiftCertificate = giftCertificateService.activateById(id);
        return takeHateoasForAdmin(activatedGiftCertificate);
    }

    /**
     * Delete gift certificate by id
     *
     * @param id - gift certificate id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> deleteGiftCertificate(@PathVariable Long id) {
        GiftCertificate deletedGiftCertificate = giftCertificateService.deleteById(id);
        return EntityModel.of(modelMapper.map(deletedGiftCertificate, GiftCertificateDto.class));
    }

    private CollectionModel<GiftCertificateDto> takeHateoasForAdminWithAllCertificates(boolean isActive, Pageable pageable) {
        List<GiftCertificate> certificates = giftCertificateService.findAllForAdmin(isActive, pageable);
        List<GiftCertificateDto> items = new ArrayList<>();

        for (GiftCertificate certificate : certificates) {
            GiftCertificateDto certificateDto = modelMapper.map(certificate, GiftCertificateDto.class);
            certificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(certificate.getId())).withRel("find by id"),
                    linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(certificate.getName())).withRel("find by name"),
                    linkTo(methodOn(GiftCertificateRestController.class).updateGiftCertificate(certificate.getId(), certificate)).withRel("update by id"),
                    linkTo(methodOn(GiftCertificateRestController.class).deleteGiftCertificate(certificate.getId())).withRel("delete by id"));
            items.add(certificateDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(GiftCertificateRestController.class).
                findAllCertificatesForAdmin(isActive, pageable)).withRel("find all certificates"));
    }

    private CollectionModel<GiftCertificateDto> takeHateoasForUserWithAllCertificates(Pageable pageable) {
        List<GiftCertificate> certificates = giftCertificateService.findAll(pageable);
        List<GiftCertificateDto> items = new ArrayList<>();

        for (GiftCertificate certificate : certificates) {
            GiftCertificateDto certificateDto = modelMapper.map(certificate, GiftCertificateDto.class);
            certificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(certificate.getId())).withRel("find by id"),
                    linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(certificate.getName())).withRel("find by name"));
            items.add(certificateDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(GiftCertificateRestController.class).
                findAllCertificates(pageable)).withRel("find all certificates"));
    }

    private CollectionModel<GiftCertificateDto> takeHateoasForAdminByTagName(List<String> tagName) {
        List<GiftCertificate> listGiftCertificate = giftCertificateService.findByTagName(tagName);
        List<GiftCertificateDto> items = new ArrayList<>();

        for (GiftCertificate certificate : listGiftCertificate) {
            GiftCertificateDto certificateDto = modelMapper.map(certificate, GiftCertificateDto.class);
            certificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(certificate.getId())).withRel("find by id"),
                    linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(certificate.getName())).withRel("find by name"),
                    linkTo(methodOn(GiftCertificateRestController.class).updateGiftCertificate(certificate.getId(), certificate)).withRel("update by id"),
                    linkTo(methodOn(GiftCertificateRestController.class).deleteGiftCertificate(certificate.getId())).withRel("delete by id"));
            items.add(certificateDto);
        }

        return CollectionModel.of(items);
    }

    private CollectionModel<GiftCertificateDto> takeHateoasForUserByTagName(List<String> tagName) {
        List<GiftCertificate> listGiftCertificate = giftCertificateService.findByTagName(tagName);
        List<GiftCertificateDto> items = new ArrayList<>();

        for (GiftCertificate certificate : listGiftCertificate) {
            GiftCertificateDto certificateDto = modelMapper.map(certificate, GiftCertificateDto.class);
            certificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(certificate.getId())).withRel("find by id"),
                    linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(certificate.getName())).withRel("find by name"));
            items.add(certificateDto);
        }

        return CollectionModel.of(items);
    }

    private EntityModel<GiftCertificateDto> takeHateoasForAdmin(GiftCertificate giftCertificate) {
        return EntityModel.of(modelMapper.map(giftCertificate, GiftCertificateDto.class),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(giftCertificate.getId())).withRel("find by id"),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(giftCertificate.getName())).withRel("find by name"),
                linkTo(methodOn(GiftCertificateRestController.class).updateGiftCertificate(giftCertificate.getId(), giftCertificate)).withRel("update by id"),
                linkTo(methodOn(GiftCertificateRestController.class).deleteGiftCertificate(giftCertificate.getId())).withRel("delete by id"));
    }

    private EntityModel<GiftCertificateDto> takeHateoasForUser(GiftCertificate giftCertificate) {
        return EntityModel.of(modelMapper.map(giftCertificate, GiftCertificateDto.class),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(giftCertificate.getId())).withRel("find by id"),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(giftCertificate.getName())).withRel("find by name"));
    }
}

