package com.epam.esm.controller;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateRestController {
    public final GiftCertificateService giftCertificateService;
    private final ModelMapper modelMapper;

    /**
     * Find list of gift certificates
     *
     * @return - page of gift certificates or empty page
     */
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAllGiftCertificates(Pageable pageable, @RequestParam(value = "isDeleted",
            required = false, defaultValue = "false") boolean isDeleted) {

        List<GiftCertificate> listGiftCertificate = giftCertificateService.findAll(pageable, isDeleted);
        return listGiftCertificate
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Find gift certificate by id
     *
     * @param id - gift certificate id
     * @return - gift certificate
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findGiftCertificateById(@PathVariable Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateService.findById(id);
        return modelMapper.map(giftCertificate.get(), GiftCertificateDto.class);
    }

    /**
     * Find gift certificate by name
     *
     * @param name - gift certificate name
     * @return - gift certificate
     */
    @GetMapping("/search/byName")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findGiftCertificateByName(@RequestParam String name) {
        Optional<GiftCertificate> giftCertificate = giftCertificateService.findByName(name);
        return modelMapper.map(giftCertificate.get(), GiftCertificateDto.class);
    }

    /**
     * Find gift certificate by tag name
     *
     * @param tagName - tag name
     * @return - list of gift certificates
     */
    @GetMapping("/search/byTag")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificateByTagName(@RequestParam List<String> tagName) {
        List<GiftCertificate> listGiftCertificate = giftCertificateService.findByTagName(tagName);
        return listGiftCertificate
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Create gift certificate
     *
     * @param giftCertificate - gift certificate
     * @return - gift certificate
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto saveGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate newGiftCertificate = giftCertificateService.save(giftCertificate);
        return modelMapper.map(newGiftCertificate, GiftCertificateDto.class);
    }

    /**
     * Update gift certificate by id
     *
     * @param id              - gift certificate id
     * @param giftCertificate - gift certificate
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificate(@PathVariable Long id,
                                                    @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate updatedGiftCertificate = giftCertificateService.updateById(id, giftCertificate);
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    /**
     * Activate gift certificate by id
     *
     * @param id        - gift certificate id
     * @param isCommand - command for activate
     */
    @PatchMapping("/active/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto activateGiftCertificate(@PathVariable Long id,
                                                      @RequestParam(value = "isCommand", defaultValue = "false") boolean isCommand) {
        GiftCertificate activatedGiftCertificate = giftCertificateService.activateById(id, isCommand);
        return modelMapper.map(activatedGiftCertificate, GiftCertificateDto.class);
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

