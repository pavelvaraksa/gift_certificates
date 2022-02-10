package com.epam.esm.controller;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Gift certificate controller.
 * Works with the gift certificate service layer.
 *
 * @see com.epam.esm.service.impl.GiftCertificateServiceImpl
 */
@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateRestController {
    public final GiftCertificateServiceImpl giftCertificateService;
    private final ModelMapper modelMapper;

    /**
     * Find list of gift certificates.
     *
     * @return - list of gift certificates or empty list.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAllGiftCertificates() {
        List<GiftCertificate> listGiftCertificate = giftCertificateService.findAll();
        return listGiftCertificate
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Find sort list of gift certificates by different columns and two sort types.
     *
     * @return - sort list of gift certificates or empty list.
     */
    @GetMapping("/sort")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAllSortedGiftCertificates(@RequestParam Set<ColumnName> column,
                                                                  @RequestParam SortType type) {
        {
            List<GiftCertificate> listGiftCertificate = giftCertificateService.findAllSorted(column, type);
            return listGiftCertificate
                    .stream()
                    .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Find gift certificate by ID.
     *
     * @param id - gift certificate ID.
     * @return - gift certificate.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findGiftCertificateById(@PathVariable Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateService.findById(id);

        return modelMapper.map(giftCertificate.get(), GiftCertificateDto.class);
    }

    /**
     * Find gift certificate by part of name or part of description.
     *
     * @param name        - part of name.
     * @param description - part of description.
     * @return - list of gift certificates or empty list.
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificateByPart(@RequestParam String name,
                                                              @RequestParam String description) {
        {
            List<GiftCertificate> giftCertificates = giftCertificateService.search(name, description);

            return giftCertificates
                    .stream()
                    .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Find gift certificate by tag name.
     *
     * @param tagName - tag name.
     * @return - list of gift certificates or empty list.
     */
    @GetMapping("/tagname/{tagName}")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificateByTagName(@PathVariable String tagName) {
        List<GiftCertificate> giftCertificates = giftCertificateService.findByTagName(tagName);
        return giftCertificates
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Create gift certificate.
     *
     * @param giftCertificate - gift certificate.
     * @return - gift certificate.
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate newGiftCertificate = giftCertificateService.create(giftCertificate);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(giftCertificate.getId())
//                .toUri();

        return modelMapper.map(newGiftCertificate, GiftCertificateDto.class);
    }

    /**
     * Update gift certificate by ID.
     *
     * @param id              - gift certificate ID.
     * @param giftCertificate - gift certificate.
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificate(@PathVariable Long id,
                                                    @RequestBody GiftCertificate giftCertificate) {

        GiftCertificate updatedGiftCertificate = giftCertificateService.updateById(id, giftCertificate);
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    /**
     * Delete gift certificate by ID.
     *
     * @param id - gift certificate ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
    }
}

