package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TagServiceImplTest {
    @Mock
    private TagRepositoryImpl tagRepository;
    private TagServiceImpl tagService;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagServiceImpl(tagRepository);
    }

    @Test
    public void createPositive() {
        String tagName = "tag_4";

        Tag createdTag = new Tag();
        createdTag.setId(4L);
        createdTag.setName(tagName);

        Tag expectedTag = new Tag();
        expectedTag.setId(4L);
        expectedTag.setName(tagName);

        Mockito.when(tagRepository.findByName(Mockito.eq(tagName))).thenReturn(Optional.empty());
        Mockito.when(tagRepository.create(createdTag)).thenReturn(expectedTag);

        Tag actualTag = tagService.create(createdTag);
        assertEquals(expectedTag, actualTag);
    }


    @Test
    public void findAllPositive() {
        List<Tag> expectedTags = Arrays.asList(
                new Tag(1L, "tag_1"),
                new Tag(2L, "tag_2"),
                new Tag(3L, "tag_3")
        );

        Mockito.when(tagRepository.findAll()).thenReturn(expectedTags);
        List<Tag> actualTags = tagService.findAll();
        assertEquals(expectedTags, actualTags);
    }

    @Test
    public void findAllNegative() {
        List<Tag> expectedTags = Arrays.asList(
                new Tag(1L, "tag_111"),
                new Tag(2L, "tag_222"),
                new Tag(3L, "tag_333")
        );

        Mockito.when(tagRepository.findAll()).thenReturn(expectedTags);
        expectedTags = new ArrayList<>();
        List<Tag> actualTags = tagService.findAll();
        assertNotEquals(expectedTags, actualTags);
    }

    @Test
    public void findByIdPositive() {
        Long requiredId = 2L;
        Tag expectedTag = new Tag(requiredId, "tag_2");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(requiredId)).thenReturn(optionalTag);
        Optional<Tag> actualTag = tagService.findById(requiredId);
        assertEquals(actualTag, optionalTag);
    }

    @Test
    public void findByIdNotNull() {
        Long requiredId = 1L;
        Tag expectedTag = new Tag(requiredId, "tag_1");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(requiredId)).thenReturn(optionalTag);
        tagService.findById(requiredId);
        assertNotNull(requiredId);
    }

    @Test
    public void findByIdNull() {
        Long requiredId = null;
        Tag expectedTag = new Tag(requiredId, "tag_1");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(requiredId)).thenReturn(optionalTag);
        tagService.findById(requiredId);
        assertNull(requiredId);
    }

    @Test
    public void findByIdWithoutThrowsException() {
        Long existsId = 3L;
        Tag expectedTag = new Tag(3L, "tag_3");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(existsId)).thenReturn(optionalTag);
        assertDoesNotThrow(() -> tagService.findById(existsId));
    }

    @Test
    public void deletePositive() {
        Long existsId = 2L;
        Tag expectedTag = new Tag(2L, "tag_2");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(existsId)).thenReturn(optionalTag);
        Mockito.when(tagRepository.deleteById(existsId)).thenReturn(true);
        tagService.deleteById(existsId);
        boolean actualResult = tagService.deleteById(existsId);
        assertTrue(actualResult);
    }

    @Test
    public void deleteNegative() {
        Long existsId = 2L;
        Long nonExistingId = 22L;
        Tag expectedTag = new Tag(2L, "tag_2");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(existsId)).thenReturn(optionalTag);
        Mockito.when(tagRepository.deleteById(nonExistingId)).thenReturn(false);
        boolean actualResult = tagService.deleteById(existsId);
        assertFalse(actualResult);
    }
}
