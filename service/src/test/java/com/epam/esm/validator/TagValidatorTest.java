package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TagValidatorTest {
    private static TagDto tagDto;

    @BeforeEach
    public void beforeAll() {
        tagDto = new TagDto();
        tagDto.setName("Tag_1");
    }

    @Test
    public void testCorrectName() {
        Assertions.assertTrue(TagValidator.isTagValid(tagDto));
    }

    @Test
    public void testIncorrectName() {
        tagDto.setName("Tag  1");
        Assertions.assertThrows(ServiceValidException.class,
                () -> TagValidator.isTagValid(tagDto));
    }

    @Test
    public void testIncorrectNameSymbols() {
        tagDto.setName("###");
        Assertions.assertThrows(ServiceValidException.class,
                () -> TagValidator.isTagValid(tagDto));
    }

    @Test
    public void testIncorrectNameMaxSize() {
        tagDto.setName("1111111111111111111111111111111");
        Assertions.assertThrows(ServiceValidException.class,
                () -> TagValidator.isTagValid(tagDto));
    }

    @Test
    public void testIncorrectNameNull() {
        tagDto.setName(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> TagValidator.isTagValid(tagDto));
    }
}
