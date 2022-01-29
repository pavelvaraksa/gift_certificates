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
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepositoryImpl giftCertificateRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificate> listGiftCertificate = giftCertificateRepository.findAll();

        return listGiftCertificate
                .stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate != null) {
            return modelMapper.map(giftCertificate, GiftCertificateDto.class);
        }

        throw new ServiceNotFoundException("Gift certificate with id " + id + " was not found");
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        StringValidator.isGiftCertificateValid(giftCertificateDto);

        log.info("Gift certificate with name " + giftCertificate.getName() + " saved");
        return modelMapper.map(giftCertificateRepository.create(giftCertificate), GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto updateById(Long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate updatedGiftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);

        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate == null) {
            String errorMessage = "Gift certificate with id " + id + " was not updated";
            log.error(errorMessage);
            throw new ServiceNotFoundException(errorMessage);
        }

        StringValidator.isGiftCertificateValid(giftCertificateDto);

        if (giftCertificateDto.getName() == null) {
            updatedGiftCertificate.setName(giftCertificate.getName());
        }

        if (giftCertificateDto.getDescription() == null) {
            updatedGiftCertificate.setDescription(giftCertificate.getDescription());
        }

        if (giftCertificateDto.getPrice() == null) {
            updatedGiftCertificate.setPrice(giftCertificate.getPrice());
        }

        if (giftCertificateDto.getDuration() == null) {
            updatedGiftCertificate.setDuration(giftCertificate.getDuration());
        }

        log.info("Gift certificate with name " + giftCertificateDto.getName() + " updated");
        return modelMapper.map(giftCertificateRepository.updateById(id, updatedGiftCertificate), GiftCertificateDto.class);
    }

    @Override
    public void deleteById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);

        if (giftCertificate == null) {
            String errorMessage = "Gift certificate with id " + id + " was not found";
            log.error(errorMessage);
            throw new ServiceNotFoundException(errorMessage);
        }

        log.info("Gift certificate with id " + id + " deleted");
        giftCertificateRepository.deleteById(id);
    }
}

