package esm.com.epam.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import esm.com.epam.repository.config.ConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringJUnitConfig(ConfigTest.class)
//@Sql(scripts = "classpath:database/schema.sql")
//@Sql(scripts = "classpath:database/data.sql")
public class GiftCertificateRepositoryImplTest {
//    private final static Long ID_POSITIVE = 1L;
//    private final static Long ID_NEGATIVE = -1L;
//    private final static Long ID_NOT_EXIST = 55L;
//    private static final String NAME_POSITIVE = "Certificate_1";
//    private static final String NAME_NEGATIVE = "Certificate  1";
//    private static final String NAME_NOT_EXIST = "Certificate_not_exist";
//    private static final String PART_NAME_POSITIVE = "Certificate";
//    private static final String PART_NAME_NEGATIVE = "-";
//    private static final String PART_DESCRIPTION_POSITIVE = "Description";
//    private static final String PART_DESCRIPTION_NEGATIVE = "-";
//    private final static Long ID_FOR_CREATE = 4L;
//    private static final String NAME_FOR_CREATE = "Certificate_4";
//    private static final String DESCRIPTION_FOR_CREATE = "Description_4";
//    private static final BigDecimal PRICE_FOR_CREATE = BigDecimal.valueOf(1.1);
//    private static final Integer DURATION_FOR_CREATE = 4;
//    private static final LocalDateTime CREATE_DATE = LocalDateTime.of(2022, 2, 5, 11, 43, 51, 126);
//    private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.of(2022, 2, 5, 11, 43, 51, 126);
//    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE;
//    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE_FIRST;
//    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE_SECOND;
//    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE_THIRD;
//    private static List<GiftCertificate> EXPECTED_GIFT_CERTIFICATES;
//    private final GiftCertificateRepositoryImpl giftCertificateRepository;
//
//    @Autowired
//    public GiftCertificateRepositoryImplTest(GiftCertificateRepositoryImpl giftCertificateRepository) {
//        this.giftCertificateRepository = giftCertificateRepository;
//    }
//
//    @BeforeEach
//    void init() {
//        EXPECTED_GIFT_CERTIFICATE = new GiftCertificate();
//        EXPECTED_GIFT_CERTIFICATE.setId(ID_FOR_CREATE);
//        EXPECTED_GIFT_CERTIFICATE.setName(NAME_FOR_CREATE);
//        EXPECTED_GIFT_CERTIFICATE.setDescription(DESCRIPTION_FOR_CREATE);
//        EXPECTED_GIFT_CERTIFICATE.setPrice(PRICE_FOR_CREATE);
//        EXPECTED_GIFT_CERTIFICATE.setDuration(DURATION_FOR_CREATE);
//        EXPECTED_GIFT_CERTIFICATE.setCreateDate(CREATE_DATE);
//        EXPECTED_GIFT_CERTIFICATE.setLastUpdateDate(LAST_UPDATE_DATE);
//
//        EXPECTED_GIFT_CERTIFICATE_FIRST = new GiftCertificate(1L,
//                "Certificate_1",
//                "Description_1",
//                BigDecimal.valueOf(1.15),
//                1,
//                LocalDateTime.of(2022, 2, 1, 13, 1, 22, 126000000),
//                LocalDateTime.of(2022, 2, 1, 13, 1, 22, 126000000),
//                null);
//
//        EXPECTED_GIFT_CERTIFICATE_SECOND = new GiftCertificate(2L,
//                "Certificate_2",
//                "Description_2",
//                BigDecimal.valueOf(2.15),
//                2,
//                LocalDateTime.of(2022, 2, 2, 13, 1, 22, 126000000),
//                LocalDateTime.of(2022, 2, 2, 13, 1, 22, 126000000),
//                null);
//
//        EXPECTED_GIFT_CERTIFICATE_THIRD = new GiftCertificate(3L,
//                "Certificate_3",
//                "Description_3",
//                BigDecimal.valueOf(3.15),
//                3,
//                LocalDateTime.of(2022, 2, 3, 13, 1, 22, 126000000),
//                LocalDateTime.of(2022, 2, 3, 13, 1, 22, 126000000),
//                null);
//
//        EXPECTED_GIFT_CERTIFICATES = Arrays.asList(
//                EXPECTED_GIFT_CERTIFICATE_FIRST,
//                EXPECTED_GIFT_CERTIFICATE_SECOND,
//                EXPECTED_GIFT_CERTIFICATE_THIRD
//        );
//    }
//
//    @Test
//    public void createPositive() {
//        GiftCertificate actualGiftCertificate = giftCertificateRepository.create(EXPECTED_GIFT_CERTIFICATE);
//        assertEquals(EXPECTED_GIFT_CERTIFICATE, actualGiftCertificate);
//    }
//
//    @Test
//    public void findAllPositive() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.findAll();
//        assertEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByIdPositive() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(ID_POSITIVE);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertTrue(actualResult);
//    }
//
//    @Test
//    public void findByIdNegative() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(ID_NEGATIVE);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertFalse(actualResult);
//    }
//
//    @Test
//    public void findByIdNotExist() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(ID_NOT_EXIST);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertFalse(actualResult);
//    }
//
//    @Test
//    public void findByNamePositive() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findByName(NAME_POSITIVE);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertTrue(actualResult);
//    }
//
//    @Test
//    public void findByNameNegative() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findByName(NAME_NEGATIVE);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertFalse(actualResult);
//    }
//
//    @Test
//    public void findByNameNotExist() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findByName(NAME_NOT_EXIST);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertFalse(actualResult);
//    }
//
//    @Test
//    public void findByPartOfNamePositive() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.findByPartName(PART_NAME_POSITIVE);
//        assertEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByPartOfNameNegative() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.findByPartName(PART_NAME_NEGATIVE);
//        assertNotEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByPartOfDescriptionPositive() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.findByPartDescription(PART_DESCRIPTION_POSITIVE);
//        assertEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByPartOfDescriptionNegative() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.findByPartDescription(PART_DESCRIPTION_NEGATIVE);
//        assertNotEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void deletePositive() {
//        boolean isResult = giftCertificateRepository.deleteById(ID_POSITIVE);
//        assertTrue(isResult);
//    }
//
//    @Test
//    public void deleteNegative() {
//        boolean isResult = giftCertificateRepository.deleteById(ID_NEGATIVE);
//        assertTrue(isResult);
//    }
//
//    @Test
//    public void deleteNotExist() {
//        giftCertificateRepository.deleteById(ID_NOT_EXIST);
//    }
//
//    @Test
//    public void findByTagIdPositive() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.findByTagId(1L);
//        List<GiftCertificate> expectedGiftCertificates = Collections.singletonList(EXPECTED_GIFT_CERTIFICATE_THIRD);
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByTagIdNegative() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.findByTagId(2L);
//        List<GiftCertificate> expectedGiftCertificates = Collections.singletonList(EXPECTED_GIFT_CERTIFICATE_FIRST);
//        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
}
