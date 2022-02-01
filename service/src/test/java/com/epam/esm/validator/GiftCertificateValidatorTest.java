package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ServiceValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class GiftCertificateValidatorTest {
    private static GiftCertificateDto giftCertificateDto;

    @BeforeEach
    public void beforeAll() {
        giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Certificate_1");
        giftCertificateDto.setDescription("Description_1");
        giftCertificateDto.setPrice(BigDecimal.valueOf(3.75));
        giftCertificateDto.setDuration(4);
    }

    @Test
    public void testCorrectAllFields() {
        Assertions.assertTrue(GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectName() {
        giftCertificateDto.setName("Certificate  1");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectNameSymbols() {
        giftCertificateDto.setName("###");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectNameMaxSize() {
        giftCertificateDto.setName("1111111111111111111111111111111");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectNameNull() {
        giftCertificateDto.setName(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectDescription() {
        giftCertificateDto.setDescription("Description  1");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectDescriptionSymbols() {
        giftCertificateDto.setDescription("!?^");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectDescriptionMaxSize() {
        giftCertificateDto.setDescription("111111111111111111111111111111111111111111111111111");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectDescriptionNull() {
        giftCertificateDto.setDescription(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectPrice() {
        giftCertificateDto.setPrice(BigDecimal.valueOf(-5.6));
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectPriceNull() {
        giftCertificateDto.setPrice(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectDuration() {
        giftCertificateDto.setDuration(-5);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectDurationNull() {
        giftCertificateDto.setDuration(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }

    @Test
    public void testIncorrectDurationZero() {
        giftCertificateDto.setDuration(0);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificateDto));
    }
}
