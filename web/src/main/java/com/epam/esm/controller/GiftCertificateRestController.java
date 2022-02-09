package com.epam.esm.controller;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
     * Find gift certificate by part of name.
     *
     * @param partName - part of part of name.
     * @return - list of gift certificates or empty list.
     */
    @GetMapping("/partname/{partName}")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificateByPartName(@PathVariable String partName) {
        List<GiftCertificate> giftCertificates = giftCertificateService.findByPartName(partName);
        return giftCertificates
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Find gift certificate by part of description.
     *
     * @param partDescription - part of description.
     * @return - list of gift certificates or empty list.
     */
    @GetMapping("/partdescription/{partDescription}")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificateByPartDescription(@PathVariable String partDescription) {
        List<GiftCertificate> giftCertificates = giftCertificateService.findByPartDescription(partDescription);
        return giftCertificates
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
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
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate newGiftCertificate = giftCertificateService.create(giftCertificate);
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

