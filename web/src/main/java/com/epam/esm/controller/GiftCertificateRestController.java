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

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateRestController {
    public final GiftCertificateServiceImpl giftCertificateService;
    private final ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAllGiftCertificates() {
        List<GiftCertificate> listGiftCertificate = giftCertificateService.findAll();
        return listGiftCertificate
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/sort")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAllSortedGiftCertificates(@RequestParam Set<ColumnName> sortBy,
                                                                  @RequestParam SortType sortType) {
        {
            List<GiftCertificate> listGiftCertificate = giftCertificateService.findAllSorted(sortBy, sortType);
            return listGiftCertificate
                    .stream()
                    .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findGiftCertificateById(@PathVariable Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateService.findById(id);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @GetMapping("/part-name/{partName}")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificateByPartName(@PathVariable String partName) {
        List<GiftCertificate> giftCertificates = giftCertificateService.findByPartName(partName);
        return giftCertificates
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/part-description/{partDescription}")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificateByPartDescription(@PathVariable String partDescription) {
        List<GiftCertificate> giftCertificates = giftCertificateService.findByPartDescription(partDescription);
        return giftCertificates
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/tag-name/{tagName}")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificateByTagName(@PathVariable String tagName) {
        List<GiftCertificate> giftCertificates = giftCertificateService.findByTagName(tagName);
        return giftCertificates
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate newGiftCertificate = giftCertificateService.create(giftCertificate);
        return modelMapper.map(newGiftCertificate, GiftCertificateDto.class);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificate(@PathVariable Long id,
                                                    @RequestBody GiftCertificate giftCertificate) {

        GiftCertificate updatedGiftCertificate = giftCertificateService.updateById(id, giftCertificate);
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
    }
}

