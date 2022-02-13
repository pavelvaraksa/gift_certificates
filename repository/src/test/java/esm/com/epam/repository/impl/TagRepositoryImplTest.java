package esm.com.epam.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import esm.com.epam.repository.config.ConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//@SpringJUnitConfig(ConfigTest.class)
//@Sql(scripts = "classpath:database/schema.sql")
//@Sql(scripts = "classpath:database/data.sql")
public class TagRepositoryImplTest {
//    private final static int EXPECTED_COUNT_TAGS_POSITIVE = 3;
//    private final static int EXPECTED_COUNT_TAGS_NEGATIVE = 5;
//    private final static long EXPECTED_TAG_ID_POSITIVE = 1;
//    private final static long EXPECTED_TAG_ID_NEGATIVE = 5;
//    private final static String TAG_NAME_1 = "tag_1";
//    private final static String TAG_NAME_2 = "tag_2";
//    private final static String TAG_NAME_3 = "tag_3";
//    private final static String EXPECTED_TAG_NAME_POSITIVE = "tag_4";
//    private final static String EXPECTED_TAG_NAME_NEGATIVE = "tag  4";
//    private static Tag EXPECTED_TAG_POSITIVE;
//    private static Tag EXPECTED_TAG_NEGATIVE;
//    private static List<Tag> EXPECTED_POSITIVE_TAGS;
//    private static List<Tag> EXPECTED_NEGATIVE_TAGS;
//    private final TagRepositoryImpl tagRepository;
//
//    @Autowired
//    public TagRepositoryImplTest(TagRepositoryImpl tagRepository) {
//        this.tagRepository = tagRepository;
//    }
//
//    @BeforeEach
//    void init() {
//        EXPECTED_TAG_POSITIVE = new Tag();
//        EXPECTED_TAG_POSITIVE.setId(EXPECTED_TAG_ID_POSITIVE);
//        EXPECTED_TAG_POSITIVE.setName(EXPECTED_TAG_NAME_POSITIVE);
//
//        EXPECTED_TAG_NEGATIVE = new Tag();
//        EXPECTED_TAG_NEGATIVE.setId(EXPECTED_TAG_ID_NEGATIVE);
//        EXPECTED_TAG_POSITIVE.setName(EXPECTED_TAG_NAME_POSITIVE);
//
//        EXPECTED_POSITIVE_TAGS = Arrays.asList(new Tag(1L, TAG_NAME_1),
//                new Tag(2L, TAG_NAME_2),
//                new Tag(3L, TAG_NAME_3));
//
//        EXPECTED_NEGATIVE_TAGS = Arrays.asList(new Tag(5L, TAG_NAME_1),
//                new Tag(6L, TAG_NAME_2),
//                new Tag(7L, TAG_NAME_3));
//    }
//
//    @Test
//    public void createTagPositive() {
//        Tag actualTag = tagRepository.create(EXPECTED_TAG_POSITIVE);
//        String actualTagName = actualTag.getName();
//        assertEquals(EXPECTED_TAG_NAME_POSITIVE, actualTagName);
//    }
//
//    @Test
//    public void createTagNegative() {
//        Tag actualTag = tagRepository.create(EXPECTED_TAG_POSITIVE);
//        String actualTagName = actualTag.getName();
//        assertNotEquals(EXPECTED_TAG_NAME_NEGATIVE, actualTagName);
//    }
//
//    @Test
//    public void createEqualsPositive() {
//        Tag actualTag = tagRepository.create(EXPECTED_TAG_POSITIVE);
//        assertEquals(EXPECTED_TAG_POSITIVE, actualTag);
//    }
//
//    @Test
//    public void createEqualsNegative() {
//        Tag actualTag = tagRepository.create(EXPECTED_TAG_POSITIVE);
//        assertNotEquals(EXPECTED_TAG_NEGATIVE, actualTag);
//    }
//
//    @Test
//    public void findAllEqualsPositive() {
//        List<Tag> actualTags = tagRepository.findAll();
//        assertEquals(EXPECTED_POSITIVE_TAGS, actualTags);
//    }
//
//    @Test
//    public void findAllEqualsNegative() {
//        List<Tag> actualTags = tagRepository.findAll();
//        assertNotEquals(EXPECTED_NEGATIVE_TAGS, actualTags);
//    }
//
//    @Test
//    public void findAllSizePositive() {
//        List<Tag> actualTags = tagRepository.findAll();
//        Integer actualCountTags = actualTags.size();
//        assertEquals(EXPECTED_COUNT_TAGS_POSITIVE, actualCountTags);
//    }
//
//    @Test
//    public void findAllSizeNegative() {
//        List<Tag> actualTags = tagRepository.findAll();
//        Integer actualCountTags = actualTags.size();
//        assertNotEquals(EXPECTED_COUNT_TAGS_NEGATIVE, actualCountTags);
//    }
}
