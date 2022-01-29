package com.epam.esm.controller;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateRestController {
    public final GiftCertificateServiceImpl giftCertificateService;

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> findAllGiftCertificates() {
        return ResponseEntity.ok(giftCertificateService.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GiftCertificateDto> findGiftCertificateById(@PathVariable Long id) {
        GiftCertificateDto giftCertificate = giftCertificateService.findById(id);
        return ResponseEntity.ok(giftCertificate);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GiftCertificateDto> createGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto newGiftCertificate = giftCertificateService.create(giftCertificateDto);
        return ResponseEntity.ok(newGiftCertificate);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GiftCertificateDto> updateGiftCertificate(@PathVariable Long id,
                                                                    @RequestBody GiftCertificateDto giftCertificate) {

        GiftCertificateDto updatedGiftCertificate = giftCertificateService.updateById(id, giftCertificate);
        return ResponseEntity.ok(updatedGiftCertificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

