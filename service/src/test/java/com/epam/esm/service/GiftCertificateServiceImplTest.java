package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.GiftCertificateToTagRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCertificateServiceImplTest {
    @Mock
    private static GiftCertificateRepositoryImpl giftCertificateRepository;
    @Mock
    private static TagRepositoryImpl tagRepository;
    @Mock
    private static GiftCertificateToTagRepositoryImpl giftCertificateToTagRepository;

    private static GiftCertificate existsGiftCertificateOne;
    private static GiftCertificate existsGiftCertificateTwo;
    private static GiftCertificate existsGiftCertificateThree;
    private static Tag tag;
    private static Set<Tag> existsTagsOne;
    private static Set<Tag> existsTagsTwo;
    private static Set<Tag> existsTagsThree;
    private GiftCertificateServiceImpl giftCertificateService;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.openMocks(this);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository, giftCertificateToTagRepository, tagRepository);

        existsGiftCertificateOne = new GiftCertificate(1L, "Certificate_1", "Description_1", BigDecimal.valueOf(1.15), 1,
                LocalDateTime.of(2022, 2, 2, 12, 15, 13, 133000000),
                LocalDateTime.of(2022, 2, 2, 12, 15, 13, 133000000),
                null);
        existsGiftCertificateTwo = new GiftCertificate(2L, "Certificate_2", "Description_2", BigDecimal.valueOf(2.25), 2,
                LocalDateTime.of(2022, 2, 3, 11, 52, 27, 431000000),
                LocalDateTime.of(2022, 2, 3, 11, 52, 27, 431000000),
                null);
        existsGiftCertificateThree = new GiftCertificate(3L, "Certificate_3", "Description_3", BigDecimal.valueOf(3.35), 3,
                LocalDateTime.of(2022, 2, 4, 6, 7, 41, 986000000),
                LocalDateTime.of(2022, 2, 4, 6, 7, 41, 986000000),
                null);

        existsTagsOne = new HashSet<>(Arrays.asList(
                new Tag(1L, "tag_1"),
                new Tag(2L, "tag_2"),
                new Tag(3L, "tag_3")
        ));
        existsTagsTwo = Collections.singleton(new Tag(2L, "tag_2"));
        existsTagsThree = Collections.singleton(new Tag(3L, "tag_3"));
        tag = new Tag(4L, "tag_4");
    }

    @Test
    public void createPositive() {
        GiftCertificate giftCertificate = new GiftCertificate(4L, "Certificate_4", "Description_4", BigDecimal.valueOf(1.15), 1,
                LocalDateTime.of(2022, 2, 6, 14, 54, 33, 345000000),
                LocalDateTime.of(2022, 2, 6, 14, 54, 33, 345000000),
                Collections.singleton(tag));

        Mockito.when(giftCertificateRepository.findByName("Certificate_4")).thenReturn(Optional.empty());
        Mockito.when(giftCertificateRepository.create(giftCertificate)).thenReturn(giftCertificate);
        Mockito.when(tagRepository.findByName("tag_4")).thenReturn(Optional.of(tag));
        Mockito.when(giftCertificateToTagRepository.findLink(4L, 4L)).thenReturn(Optional.empty());
        Mockito.when(giftCertificateToTagRepository.createLink(4L, 4L)).thenReturn(true);
        assertNotNull(giftCertificateService.create(giftCertificate));
    }

    @Test
    public void findAllPositive() {
        List<GiftCertificate> giftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree
        );

        Mockito.when(giftCertificateRepository.findAll()).thenReturn(giftCertificates);

        existsGiftCertificateOne.setTags(existsTagsOne);
        existsGiftCertificateTwo.setTags(existsTagsTwo);
        existsGiftCertificateThree.setTags(existsTagsThree);

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
        Mockito.when(giftCertificateRepository.findById(certificateId)).thenReturn(Optional.of(existsGiftCertificateOne));
        Mockito.when(tagRepository.findByGiftCertificateId(certificateId)).thenReturn(existsTagsOne);
        existsGiftCertificateOne.setTags(existsTagsOne);
        giftCertificateService.findById(certificateId);
        assertNotNull(certificateId);
    }

    @Test
    public void findByIdNull() {
        Long certificateId = null;
        Mockito.when(giftCertificateRepository.findById(certificateId)).thenReturn(Optional.of(existsGiftCertificateOne));
        Mockito.when(tagRepository.findByGiftCertificateId(certificateId)).thenReturn(existsTagsOne);
        existsGiftCertificateOne.setTags(existsTagsOne);
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
    public void findByTagNamePositive() {
        Long tagId = 1L;
        String existingTagName = "tag_1";
        Tag tag = new Tag(tagId, existingTagName);
        Optional<Tag> optionalTag = Optional.of(tag);

        Mockito.when(tagRepository.findByName(existingTagName)).thenReturn(optionalTag);
        List<GiftCertificate> giftCertificates = Collections.singletonList(existsGiftCertificateOne);

        Mockito.when(giftCertificateRepository.findByTagId(tagId)).thenReturn(giftCertificates);
        Mockito.when(tagRepository.findByGiftCertificateId(1L)).thenReturn(existsTagsOne);

        existsGiftCertificateOne.setTags(existsTagsOne);
        List<GiftCertificate> expectedGiftCertificates = Collections.singletonList(existsGiftCertificateOne);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findByTagName(existingTagName);
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    public void findByTagNameThrowsException() {
        String tagNameNotFound = "not_found";
        Mockito.when(tagRepository.findByName(tagNameNotFound)).thenThrow(ServiceNotFoundException.class);
        Throwable throwable = assertThrows(ServiceNotFoundException.class, () -> giftCertificateService.findByTagName(tagNameNotFound));
        assertNotNull(throwable);
    }
}
