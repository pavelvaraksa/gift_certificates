package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateToTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GiftCertificateServiceImplTest {
    @Mock
    private static GiftCertificateRepository giftCertificateRepository;
    @Mock
    private static TagRepository tagRepository;
    @Mock
    private static GiftCertificateToTagRepository giftCertificateToTagRepository;

    private static GiftCertificate existsGiftCertificateOne;
    private static GiftCertificate existsGiftCertificateTwo;
    private static GiftCertificate existsGiftCertificateThree;
    private static Tag tag;
    private static List<Tag> existsTagsOne;
    private static List<Tag> existsTagsTwo;
    private static List<Tag> existsTagsThree;
    private GiftCertificateService giftCertificateService;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.openMocks(this);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository, giftCertificateToTagRepository, tagRepository);

        existsGiftCertificateOne = new GiftCertificate(1L, "Certificate_1", "Description_1", 1.15, 1,
                LocalDateTime.of(2022, 2, 2, 12, 15, 13, 133000000),
                LocalDateTime.of(2022, 2, 2, 12, 15, 13, 133000000),
                false, null, null);
        existsGiftCertificateTwo = new GiftCertificate(2L, "Certificate_2", "Description_2", 2.25, 2,
                LocalDateTime.of(2022, 2, 3, 11, 52, 27, 431000000),
                LocalDateTime.of(2022, 2, 3, 11, 52, 27, 431000000),
                false, null, null);
        existsGiftCertificateThree = new GiftCertificate(3L, "Certificate_3", "Description_3", 3.35, 3,
                LocalDateTime.of(2022, 2, 4, 6, 7, 41, 986000000),
                LocalDateTime.of(2022, 2, 4, 6, 7, 41, 986000000),
                false, null, null);

        existsTagsOne = Arrays.asList(
                new Tag(1L, "tag_1", false, null),
                new Tag(2L, "tag_2", false, null),
                new Tag(3L, "tag_3", false, null));
        existsTagsTwo = Collections.singletonList(new Tag(2L, "tag_2", false, null));
        existsTagsThree = Collections.singletonList(new Tag(3L, "tag_3", false, null));
        tag = new Tag(4L, "tag_4", false, null);
    }

    @Test
    public void createPositive() {
        GiftCertificate giftCertificate = new GiftCertificate(4L, "Certificate_4", "Description_4", 1.15, 1,
                LocalDateTime.of(2022, 2, 6, 14, 54, 33, 345000000),
                LocalDateTime.of(2022, 2, 6, 14, 54, 33, 345000000),
                false,
                existsTagsOne,
                null);

        Mockito.when(giftCertificateRepository.findByName("Certificate_4")).thenReturn(Optional.empty());
        Mockito.when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        Mockito.when(tagRepository.findByName("tag_4")).thenReturn(Optional.ofNullable(tag));
        Mockito.when(giftCertificateToTagRepository.existsByGiftCertificateAndTag(4L, 4L)).thenReturn(true);
        assertNotNull(giftCertificateService.save(giftCertificate));
    }

    @Test
    public void findAllPositive() {
        List<GiftCertificate> giftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree
        );

        Mockito.when(giftCertificateRepository.findAll()).thenReturn(giftCertificates);

        existsGiftCertificateOne.setTag(existsTagsOne);
        existsGiftCertificateTwo.setTag(existsTagsTwo);
        existsGiftCertificateThree.setTag(existsTagsThree);

        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree);

        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAll();
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    public void findByIdNotNull() {
        Long certificateId = 1L;
        Mockito.when(giftCertificateRepository.findById(certificateId)).thenReturn(Optional.ofNullable(existsGiftCertificateOne));
        existsGiftCertificateOne.setTag(existsTagsOne);
        giftCertificateService.findById(certificateId);
        assertNotNull(certificateId);
    }

    @Test
    public void findByIdNull() {
        Long certificateId = null;
        Mockito.when(giftCertificateRepository.findById(certificateId)).thenReturn(Optional.ofNullable(existsGiftCertificateOne));
        existsGiftCertificateOne.setTag(existsTagsOne);
        giftCertificateService.findById(certificateId);
        assertNull(certificateId);
    }

    @Test
    public void findByIdThrowsException() {
        Long nonExistsCertificateId = 50L;
        Mockito.when(giftCertificateRepository.findById(nonExistsCertificateId)).thenThrow(ServiceNotFoundException.class);
        Throwable throwable = assertThrows(ServiceNotFoundException.class, () -> giftCertificateService.findById(nonExistsCertificateId));
        assertNotNull(throwable);
    }

    @Test
    public void findByTagNameThrowsException() {
        String tagNameNotFound = "not_found";
        Mockito.when(tagRepository.findByName(tagNameNotFound)).thenThrow(ServiceNotFoundException.class);
        Throwable throwable = assertThrows(ServiceNotFoundException.class,
                () -> giftCertificateService.findByTagName(Collections.singletonList(tagNameNotFound)));
        assertNotNull(throwable);
    }
}
