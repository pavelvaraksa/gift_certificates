package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Log4j2
@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String FIND_ALL_QUERY = "select * from tag";
    private static final String FIND_BY_ID_QUERY = "select * from tag where id = :id";
    private static final String CREATE_QUERY = "insert into tag (name) values (:name)";
    private static final String DELETE_QUERY = "delete from tag where id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TagRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private Tag getTagRowMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getLong(ID));
        tag.setName(resultSet.getString(NAME));
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        log.info("Invoking method 'find all tags'.");
        return namedParameterJdbcTemplate.query(FIND_ALL_QUERY, this::getTagRowMapper);
    }

    @Override
    public Tag findById(Long key) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", key);

        log.info("Invoking method 'find tag by id'.");
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_QUERY, parameterSource, this::getTagRowMapper);
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", tag.getName());

        namedParameterJdbcTemplate.update(CREATE_QUERY, parameterSource, keyHolder, new String[]{"id"});
        long createdTagId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        log.info("Invoking method 'create tag'.");
        return findById(createdTagId);
    }

    @Override
    public Tag updateById(Long id, Tag object) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        log.info("Invoking method 'delete tag'.");
        namedParameterJdbcTemplate.update(DELETE_QUERY, parameterSource);
    }
}
