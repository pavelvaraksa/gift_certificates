package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.*;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.StringValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepositoryImpl giftCertificateRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateRepository.findAll();
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        Optional<GiftCertificate> giftCertificate = Optional.ofNullable(giftCertificateRepository.findById(id));

        if (giftCertificate.isPresent()) {
            return modelMapper.map(giftCertificate.get(), GiftCertificateDto.class);
        }

        throw new ServiceValidException("mistake");
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);

        StringValidator.isGiftCertificateValid(giftCertificateDto);

        log.info("Gift certificate with name " + giftCertificate.getName() + " saved.");
        return modelMapper.map(giftCertificateRepository.create(giftCertificate), GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto updateById(Long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate updatedGiftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);

        GiftCertificate giftCertificateById = giftCertificateRepository.findById(id);

        if (giftCertificateDto.getName() == null) {
            updatedGiftCertificate.setName(giftCertificateById.getName());
        }

        if (giftCertificateDto.getDescription() == null) {
            updatedGiftCertificate.setDescription(giftCertificateById.getDescription());
        }

        if (giftCertificateDto.getPrice() == null) {
            updatedGiftCertificate.setPrice(giftCertificateById.getPrice());
        }

        if (giftCertificateDto.getDuration() == null) {
            updatedGiftCertificate.setDuration(giftCertificateById.getDuration());
        }

        log.info("Gift certificate with name " + giftCertificateDto.getName() + " updated.");
        return modelMapper.map(giftCertificateRepository.updateById(id, updatedGiftCertificate), GiftCertificateDto.class);
    }

    @Override
    public void deleteById(Long id) {
        Optional<GiftCertificate> giftCertificate = Optional.ofNullable(giftCertificateRepository.findById(id));

        if (giftCertificate.isPresent()) {
            log.info("Gift certificate with id " + id + " deleted.");
            giftCertificateRepository.deleteById(id);
        } else {
            throw new ServiceValidException("not found");
        }
    }
}
