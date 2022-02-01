package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.*;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.epam.esm.exception.MessageException.CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.MessageException.CERTIFICATE_EXIST;

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

        if (giftCertificate == null) {
            log.error("Gift certificate with id " + id + " was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto);

        List<GiftCertificate> giftCertificateByName = giftCertificateRepository.findByName(giftCertificateDto.getName());

        if (!giftCertificateByName.isEmpty()) {
            log.error("Gift certificate name " + giftCertificateDto.getName() + " already exist");
            throw new ServiceExistException(CERTIFICATE_EXIST);
        }

        log.info("Gift certificate with name " + giftCertificate.getName() + " saved");
        return modelMapper.map(giftCertificateRepository.create(giftCertificate), GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto updateById(Long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate updatedGiftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);

        GiftCertificate giftCertificateById = giftCertificateRepository.findById(id);

        if (giftCertificateById == null) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        List<GiftCertificate> GiftCertificateByName = giftCertificateRepository.findByName(giftCertificateDto.getName());

        if (!GiftCertificateByName.isEmpty()) {
            log.error("Gift certificate name " + giftCertificateDto.getName() + " already exist");
            throw new ServiceExistException(CERTIFICATE_EXIST);
        }

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

        GiftCertificateValidator.isGiftCertificateValid(modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class));

        log.info("Gift certificate with name " + updatedGiftCertificate.getName() + " updated");
        return modelMapper.map(giftCertificateRepository.updateById(id, updatedGiftCertificate), GiftCertificateDto.class);
    }

    @Override
    public void deleteById(Long id) {
        GiftCertificate giftCertificateById = giftCertificateRepository.findById(id);

        if (giftCertificateById == null) {
            log.error("Gift certificate was not found");
            throw new ServiceNotFoundException(CERTIFICATE_NOT_FOUND);
        }

        log.info("Gift certificate with id " + id + " deleted");
        giftCertificateRepository.deleteById(id);
    }
}

