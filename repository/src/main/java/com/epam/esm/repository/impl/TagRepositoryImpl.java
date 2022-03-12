package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Tag repository implementation.
 */
@Log4j2
@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String FIND_ALL_QUERY = "select * from tag";
    private static final String FIND_BY_ID_QUERY = "select * from tag where id = ?";
    private static final String FIND_BY_NAME_QUERY = "select * from tag where name = ?";
    private static final String CREATE_QUERY = "insert into tag (name) values (:name)";
    private static final String DELETE_QUERY = "delete from tag where id = :id";
    private static final String FIND_TAGS_BY_CERTIFICATE_ID_QUERY = "select id, name from tag " +
            " join gift_certificate_to_tag on gift_certificate_to_tag.tag_id = id " +
            " where gift_certificate_to_tag.gift_certificate_id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_TO_TAG_BY_ID_QUERY = "delete from gift_certificate_to_tag " +
            "where gift_certificate_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TagRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    private Tag getTagRowMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getLong(ID));
        tag.setName(resultSet.getString(NAME));
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL_QUERY, this::getTagRowMapper);
    }

    @Override
    public Optional<Tag> findById(Long key) {
        Optional<Tag> optionalTag;

        try {
            optionalTag = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
                    new BeanPropertyRowMapper<>(Tag.class), key));
        } catch (EmptyResultDataAccessException e) {
            log.error("Method 'find tag by id' was not implemented");
            optionalTag = Optional.empty();
        }

        return optionalTag;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Optional<Tag> optionalTag;

        try {
            optionalTag = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_NAME_QUERY, new BeanPropertyRowMapper<>(Tag.class), name));
        } catch (EmptyResultDataAccessException e) {
            optionalTag = Optional.empty();
        }

        return optionalTag;
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(NAME, tag.getName());

        namedParameterJdbcTemplate.update(CREATE_QUERY, parameterSource, keyHolder, new String[]{"id"});
        long createdTagId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        tag.setId(createdTagId);

        return tag;
    }

    @Override
    public Tag updateById(Long id, Tag object) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, id);

        namedParameterJdbcTemplate.update(DELETE_QUERY, parameterSource);
        return true;
    }

    @Override
    public Set<Tag> findByGiftCertificateId(Long id) {
        return new HashSet<>(jdbcTemplate.query(FIND_TAGS_BY_CERTIFICATE_ID_QUERY, new BeanPropertyRowMapper<>(Tag.class), id));
    }

    @Override
    public void deleteAllTagsByGiftCertificateId(Long giftCertificateId) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TO_TAG_BY_ID_QUERY, giftCertificateId);
    }
}
