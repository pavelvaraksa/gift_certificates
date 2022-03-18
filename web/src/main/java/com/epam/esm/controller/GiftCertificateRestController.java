package com.epam.esm.controller;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ColumnCertificateName;
import com.epam.esm.util.SortType;
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
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateRestController {
    public final GiftCertificateService giftCertificateService;
    public final TagService tagService;
    private final ModelMapper modelMapper;

    /**
     * Find certificates with pagination, sorting and info about deleted certificates
     *
     * @param pageable  - pagination config
     * @param column    - certificate column
     * @param sort      - sort type
     * @param isDeleted - info about deleted certificates
     * @return - list of certificates or empty list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GiftCertificateDto> findAllCertificates(@PageableDefault(size = 2) Pageable pageable,
                                                                   @RequestParam(value = "column", defaultValue = "ID") Set<ColumnCertificateName> column,
                                                                   @RequestParam(value = "sort", defaultValue = "ASC") SortType sort,
                                                                   @RequestParam(value = "isDeleted", defaultValue = "false") boolean isDeleted) {
        List<GiftCertificate> certificates = giftCertificateService.findAll(pageable, column, sort, isDeleted);
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
                findAllCertificates(pageable, column, sort, isDeleted)).withRel("find all certificates"));
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
        Optional<GiftCertificate> giftCertificate = giftCertificateService.findById(id);
        return EntityModel.of(modelMapper.map(giftCertificate.get(), GiftCertificateDto.class),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(giftCertificate.get().getId())).withRel("find by id"),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(giftCertificate.get().getName())).withRel("find by name"),
                linkTo(methodOn(GiftCertificateRestController.class).updateGiftCertificate(giftCertificate.get().getId(), giftCertificate.get())).withRel("update by id"),
                linkTo(methodOn(GiftCertificateRestController.class).deleteGiftCertificate(giftCertificate.get().getId())).withRel("delete by id"));
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
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(giftCertificate.get().getId())).withRel("find by id"),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(giftCertificate.get().getName())).withRel("find by name"),
                linkTo(methodOn(GiftCertificateRestController.class).updateGiftCertificate(giftCertificate.get().getId(), giftCertificate.get())).withRel("update by id"),
                linkTo(methodOn(GiftCertificateRestController.class).deleteGiftCertificate(giftCertificate.get().getId())).withRel("delete by id"));
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
            certificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(certificate.getId())).withRel("find by id"),
                    linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(certificate.getName())).withRel("find by name"),
                    linkTo(methodOn(GiftCertificateRestController.class).updateGiftCertificate(certificate.getId(), certificate)).withRel("update by id"),
                    linkTo(methodOn(GiftCertificateRestController.class).deleteGiftCertificate(certificate.getId())).withRel("delete by id"));
            items.add(certificateDto);
        }

        return CollectionModel.of(items);
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
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(newGiftCertificate.getId())).withRel("find by id"),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(newGiftCertificate.getName())).withRel("find by name"),
                linkTo(methodOn(GiftCertificateRestController.class).updateGiftCertificate(newGiftCertificate.getId(), newGiftCertificate)).withRel("update by id"),
                linkTo(methodOn(GiftCertificateRestController.class).deleteGiftCertificate(newGiftCertificate.getId())).withRel("delete by id"));
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
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(id)).withRel("find by id"),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(updatedGiftCertificate.getName())).withRel("find by name"),
                linkTo(methodOn(GiftCertificateRestController.class).updateGiftCertificate(id, updatedGiftCertificate)).withRel("update by id"),
                linkTo(methodOn(GiftCertificateRestController.class).deleteGiftCertificate(id)).withRel("delete by id"));
    }

    /**
     * Activate gift certificate by id
     *
     * @param id        - gift certificate id
     * @param isCommand - command for activate
     */
    @PatchMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> activateGiftCertificate(@PathVariable Long id,
                                                                   @RequestParam(value = "isCommand", defaultValue = "false") boolean isCommand) {
        GiftCertificate activatedGiftCertificate = giftCertificateService.activateById(id, isCommand);
        return EntityModel.of(modelMapper.map(activatedGiftCertificate, GiftCertificateDto.class),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateById(id)).withRel("find by id"),
                linkTo(methodOn(GiftCertificateRestController.class).findGiftCertificateByName(activatedGiftCertificate.getName())).withRel("find by name"),
                linkTo(methodOn(GiftCertificateRestController.class).updateGiftCertificate(id, activatedGiftCertificate)).withRel("update by id"),
                linkTo(methodOn(GiftCertificateRestController.class).deleteGiftCertificate(id)).withRel("delete by id"));
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
}

