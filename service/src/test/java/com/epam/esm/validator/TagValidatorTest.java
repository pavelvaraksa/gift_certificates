package com.epam.esm.validator;

import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ServiceValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TagValidatorTest {
    private Tag tag;

    @BeforeEach
    public void beforeAll() {
        tag = new Tag();
        tag.setName("Tag_1");
    }

    @Test
    public void testCorrectName() {
        Assertions.assertTrue(TagValidator.isTagValid(tag));
    }

    @Test
    public void testIncorrectName() {
        tag.setName("Tag  1");
        Assertions.assertThrows(ServiceValidException.class,
                () -> TagValidator.isTagValid(tag));
    }

    @Test
    public void testIncorrectNameSymbols() {
        tag.setName("###");
        Assertions.assertThrows(ServiceValidException.class,
                () -> TagValidator.isTagValid(tag));
    }

    @Test
    public void testIncorrectNameMaxSize() {
        tag.setName("1111111111111111111111111111111");
        Assertions.assertThrows(ServiceValidException.class,
                () -> TagValidator.isTagValid(tag));
    }

    @Test
    public void testIncorrectNameNull() {
        tag.setName(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> TagValidator.isTagValid(tag));
    }
}
