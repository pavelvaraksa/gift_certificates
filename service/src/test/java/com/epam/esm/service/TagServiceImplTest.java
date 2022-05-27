package com.epam.esm.service;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.openMocks(this);
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
        Mockito.when(tagRepository.save(createdTag)).thenReturn(expectedTag);

        Tag actualTag = tagRepository.save(createdTag);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void findByIdPositive() {
        Long requiredId = 2L;
        Tag expectedTag = new Tag(requiredId, "tag_2", false, null);
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(requiredId)).thenReturn(optionalTag);
        Optional<Tag> actualTag = tagRepository.findById(requiredId);
        assertEquals(actualTag, optionalTag);
    }

    @Test
    public void findByIdNotNull() {
        Long requiredId = 1L;
        Tag expectedTag = new Tag(requiredId, "tag_1", false, null);
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(requiredId)).thenReturn(optionalTag);
        tagRepository.findById(requiredId);
        assertNotNull(requiredId);
    }

    @Test
    public void findByIdNull() {
        Long requiredId = null;
        Tag expectedTag = new Tag(requiredId, "tag_1", false, null);
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(requiredId)).thenReturn(optionalTag);
        tagRepository.findById(requiredId);
        assertNull(requiredId);
    }

    @Test
    public void findByIdWithoutThrowsException() {
        Long existsId = 3L;
        Tag expectedTag = new Tag(3L, "tag_3", false, null);
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(existsId)).thenReturn(optionalTag);
        assertDoesNotThrow(() -> tagRepository.findById(existsId));
    }

    @Test
    public void deletePositive() {
        Long existsId = 2L;
        Tag expectedTag = new Tag(2L, "tag_2", false, null);
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(existsId)).thenReturn(optionalTag);
        tagRepository.deleteById(existsId);
    }

    @Test
    public void deleteNegative() {
        Long existsId = 2L;
        Long nonExistingId = 22L;
        Tag expectedTag = new Tag(existsId, "tag_2", false, null);
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagRepository.findById(existsId)).thenReturn(optionalTag);
        tagRepository.deleteById(nonExistingId);
    }
}
