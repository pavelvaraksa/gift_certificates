package com.epam.esm.controller;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateRestController {
    public final GiftCertificateService giftCertificateService;
    public final TagService tagService;
    private final ModelMapper modelMapper;

//    /**
//     * Find list of gift certificates
//     *
//     * @return - page of gift certificates or empty page
//     */
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CollectionModel<GiftCertificateDto> findAllGiftCertificates(@PageableDefault(size = 5) Pageable pageable,
//                                                                       @RequestParam(value = "isDeleted",
//                                                                               required = false, defaultValue = "false") boolean isDeleted) {
//        List<GiftCertificate> certificates = giftCertificateService.findAll(pageable, isDeleted);
//        List<GiftCertificateDto> items = new ArrayList<>();
//
//        for (GiftCertificate certificate : certificates) {
//            GiftCertificateDto certificateDto = modelMapper.map(certificate, GiftCertificateDto.class);
//            certificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(certificate.getId())).withSelfRel(),
//                    linkTo(GiftCertificateRestController.class).slash("search/name?name=" + certificate.getName()).withSelfRel());
//            items.add(certificateDto);
//        }
//
//        return CollectionModel.of(items, linkTo(GiftCertificateRestController.class).withRel("certificates"));
//    }

    /**
     * Find gift certificate by id
     *
     * @param id - gift certificate id
     * @return - gift certificate
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> findGiftCertificateById(@PathVariable Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateService.findById(id);
        return EntityModel.of(modelMapper.map(giftCertificate.get(), GiftCertificateDto.class),
                linkTo(GiftCertificateRestController.class).slash(id).withSelfRel(),
                linkTo(GiftCertificateRestController.class).slash("search/name?name=" + giftCertificate.get().getName()).withSelfRel(),
                linkTo(GiftCertificateRestController.class).withRel("tags"));
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
        Optional<GiftCertificate> giftCertificate = giftCertificateService.findByName(name);
        return EntityModel.of(modelMapper.map(giftCertificate.get(), GiftCertificateDto.class),
                linkTo(GiftCertificateRestController.class).slash(giftCertificate.get().getId()).withSelfRel(),
                linkTo(GiftCertificateRestController.class).slash("search/name?name=" + giftCertificate.get().getName()).withSelfRel(),
                linkTo(GiftCertificateRestController.class).withRel("tags"));
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
        List<GiftCertificate> listGiftCertificate = giftCertificateService.findByTagName(tagName);
        List<GiftCertificateDto> items = new ArrayList<>();

        for (GiftCertificate certificate : listGiftCertificate) {
            GiftCertificateDto certificateDto = modelMapper.map(certificate, GiftCertificateDto.class);
            certificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(certificate.getId())).withSelfRel(),
                    linkTo(GiftCertificateRestController.class).slash("search/name?name=" + certificate.getName()).withSelfRel());
            items.add(certificateDto);
        }

        return CollectionModel.of(items, linkTo(GiftCertificateRestController.class).withRel("certificates"));
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
        return EntityModel.of(modelMapper.map(newGiftCertificate, GiftCertificateDto.class),
                linkTo(GiftCertificateRestController.class).slash(newGiftCertificate.getId()).withSelfRel(),
                linkTo(GiftCertificateRestController.class).slash("search/name?name=" + newGiftCertificate.getName()).withSelfRel(),
                linkTo(GiftCertificateRestController.class).withRel("tags"));
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
        return EntityModel.of(modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class),
                linkTo(GiftCertificateRestController.class).slash(giftCertificate.getId()).withSelfRel(),
                linkTo(GiftCertificateRestController.class).slash("search/name?name=" + giftCertificate.getName()).withSelfRel(),
                linkTo(GiftCertificateRestController.class).withRel("tags"));
    }

    /**
     * Activate gift certificate by id
     *
     * @param id        - gift certificate id
     * @param isCommand - command for activate
     */
    @PatchMapping("/active/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> activateGiftCertificate(@PathVariable Long id,
                                                                   @RequestParam(value = "isCommand", defaultValue = "false") boolean isCommand) {
        GiftCertificate activatedGiftCertificate = giftCertificateService.activateById(id, isCommand);
        return EntityModel.of(modelMapper.map(activatedGiftCertificate, GiftCertificateDto.class),
                linkTo(GiftCertificateRestController.class).slash(activatedGiftCertificate.getId()).withSelfRel(),
                linkTo(GiftCertificateRestController.class).slash("search/name?name=" + activatedGiftCertificate.getName()).withSelfRel(),
                linkTo(GiftCertificateRestController.class).withRel("tags"));
    }

    /**
     * Delete gift certificate by id
     *
     * @param id - gift certificate id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
    }
}

