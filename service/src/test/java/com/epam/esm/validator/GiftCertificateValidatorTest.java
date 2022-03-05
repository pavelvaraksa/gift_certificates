package com.epam.esm.validator;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.ServiceValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GiftCertificateValidatorTest {
    private GiftCertificate giftCertificate;

    @BeforeEach
    public void beforeAll() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName("Certificate_1");
        giftCertificate.setDescription("Description_1");
        giftCertificate.setCurrentPrice(3.75D);
        giftCertificate.setDuration(4);
    }

    @Test
    public void testCorrectAllFields() {
        Assertions.assertTrue(GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectName() {
        giftCertificate.setName("Certificate  1");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectNameSymbols() {
        giftCertificate.setName("###");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectNameMaxSize() {
        giftCertificate.setName("1111111111111111111111111111111");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectNameNull() {
        giftCertificate.setName(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectDescription() {
        giftCertificate.setDescription("Description  1");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectDescriptionSymbols() {
        giftCertificate.setDescription("!?^");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectDescriptionMaxSize() {
        giftCertificate.setDescription("111111111111111111111111111111111111111111111111111");
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectDescriptionNull() {
        giftCertificate.setDescription(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectPrice() {
        giftCertificate.setCurrentPrice(-5.6D);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectPriceNull() {
        giftCertificate.setCurrentPrice(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectDuration() {
        giftCertificate.setDuration(-5);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectDurationNull() {
        giftCertificate.setDuration(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }

    @Test
    public void testIncorrectDurationZero() {
        giftCertificate.setDuration(0);
        Assertions.assertThrows(ServiceValidException.class,
                () -> GiftCertificateValidator.isGiftCertificateValid(giftCertificate));
    }
}
