package com.epam.esm.controller;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateRestController {
    public final GiftCertificateServiceImpl giftCertificateService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> findAllGiftCertificates() {
        List<GiftCertificate> listGiftCertificate = giftCertificateService.findAll();
        return ResponseEntity.ok(listGiftCertificate
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GiftCertificateDto> findGiftCertificateById(@PathVariable Long id) {
        GiftCertificate giftCertificate = giftCertificateService.findById(id);
        return ResponseEntity.ok(modelMapper.map(giftCertificate, GiftCertificateDto.class));
    }

    @GetMapping("/part_name/{partName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GiftCertificateDto>> findGiftCertificateByPartName(@PathVariable String partName) {
        List<GiftCertificate> giftCertificates = giftCertificateService.findByPartName(partName);
        return ResponseEntity.ok(giftCertificates
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/part_description/{partDescription}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GiftCertificateDto>> findGiftCertificateByPartDescription(@PathVariable String partDescription) {
        List<GiftCertificate> giftCertificates = giftCertificateService.findByPartDescription(partDescription);
        return ResponseEntity.ok(giftCertificates
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GiftCertificateDto> createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate newGiftCertificate = giftCertificateService.create(giftCertificate);
        return ResponseEntity.ok(modelMapper.map(newGiftCertificate, GiftCertificateDto.class));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GiftCertificateDto> updateGiftCertificate(@PathVariable Long id,
                                                                    @RequestBody GiftCertificate giftCertificate) {

        GiftCertificate updatedGiftCertificate = giftCertificateService.updateById(id, giftCertificate);
        return ResponseEntity.ok(modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

