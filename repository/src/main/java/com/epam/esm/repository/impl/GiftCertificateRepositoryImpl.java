package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
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
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";

    private static final String FIND_ALL_QUERY = "select * from gift_certificate";
    private static final String FIND_BY_ID_QUERY = "select * from gift_certificate where id = :id";
    private static final String FIND_BY_NAME_QUERY = "select * from gift_certificate where name = ?";
    private static final String FIND_BY_PART_NAME_QUERY = "select * from gift_certificate where name like :name";
    private static final String FIND_BY_PART_DESCRIPTION_QUERY = "select * from gift_certificate where description like :description";
    private static final String FIND_BY_TAG_ID_QUERY = "select * from gift_certificate inner join gift_certificate_to_tag on " +
            " gift_certificate.id = gift_certificate_to_tag.gift_certificate_id";
    private static final String CREATE_QUERY = "insert into gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) " +
            "values (:name, :description, :price, :duration, :create_date, :last_update_date);";
    private static final String UPDATE_QUERY = "update gift_certificate set " +
            "name = :name, " +
            "description = :description, " +
            "price = :price, " +
            "duration = :duration, " +
            "last_update_date = :last_update_date " +
            "where id = :id";
    private static final String DELETE_QUERY = "delete from gift_certificate where id = :id";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GiftCertificateRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    private GiftCertificate getGiftCertificateRowMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(resultSet.getLong(ID));
        giftCertificate.setName(resultSet.getString(NAME));
        giftCertificate.setDescription(resultSet.getString(DESCRIPTION));
        giftCertificate.setPrice(resultSet.getBigDecimal(PRICE));
        giftCertificate.setDuration(resultSet.getInt(DURATION));
        giftCertificate.setCreateDate(resultSet.getTimestamp(CREATE_DATE).toLocalDateTime());
        giftCertificate.setLastUpdateDate(resultSet.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL_QUERY, this::getGiftCertificateRowMapper);
    }

    @Override
    public GiftCertificate findById(Long key) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, key);
        GiftCertificate giftCertificate = null;

        try {
            giftCertificate = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_QUERY, parameterSource, this::getGiftCertificateRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            log.error("Method 'find gift certificate by id' was not implemented");
        }

        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Optional<GiftCertificate> optionalGiftCertificate;

        try {
            optionalGiftCertificate = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_NAME_QUERY,
                    new BeanPropertyRowMapper<>(GiftCertificate.class), name));
        } catch (EmptyResultDataAccessException e) {
            optionalGiftCertificate = Optional.empty();
        }

        return optionalGiftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findByPartName(String name) {
        Optional<GiftCertificate> optionalGiftCertificate;

        try {
            optionalGiftCertificate = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_PART_NAME_QUERY,
                    new BeanPropertyRowMapper<>(GiftCertificate.class), name));
        } catch (EmptyResultDataAccessException ex) {
            optionalGiftCertificate = Optional.empty();
        }

        return optionalGiftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findByPartDescription(String description) {
        Optional<GiftCertificate> optionalGiftCertificate;

        try {
            optionalGiftCertificate = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_PART_DESCRIPTION_QUERY,
                    new BeanPropertyRowMapper<>(GiftCertificate.class), description));
        } catch (EmptyResultDataAccessException ex) {
            optionalGiftCertificate = Optional.empty();
        }

        return optionalGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findByTagId(Long id) {
        return jdbcTemplate.query(FIND_BY_TAG_ID_QUERY, new BeanPropertyRowMapper<>(GiftCertificate.class), id);
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        Timestamp createTimestamp = Timestamp.from(Instant.now());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(NAME, giftCertificate.getName());
        parameterSource.addValue(DESCRIPTION, giftCertificate.getDescription());
        parameterSource.addValue(PRICE, giftCertificate.getPrice());
        parameterSource.addValue(DURATION, giftCertificate.getDuration());
        parameterSource.addValue(CREATE_DATE, createTimestamp);
        parameterSource.addValue(LAST_UPDATE_DATE, createTimestamp);

        namedParameterJdbcTemplate.update(CREATE_QUERY, parameterSource, keyHolder, new String[]{"id"});
        long createdGiftCertificateId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(createdGiftCertificateId);
    }

    @Override
    public GiftCertificate updateById(Long id, GiftCertificate giftCertificate) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        Timestamp updateTimestamp = Timestamp.from(Instant.now());

        parameterSource.addValue(NAME, giftCertificate.getName());
        parameterSource.addValue(DESCRIPTION, giftCertificate.getDescription());
        parameterSource.addValue(PRICE, giftCertificate.getPrice());
        parameterSource.addValue(DURATION, giftCertificate.getDuration());
        parameterSource.addValue(LAST_UPDATE_DATE, updateTimestamp);
        parameterSource.addValue(ID, id);

        namedParameterJdbcTemplate.update(UPDATE_QUERY, parameterSource);
        return findById(id);
    }

    @Override
    public void deleteById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, id);
        namedParameterJdbcTemplate.update(DELETE_QUERY, parameterSource);
    }
}

