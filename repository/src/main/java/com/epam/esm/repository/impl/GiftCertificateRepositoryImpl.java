package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import lombok.extern.log4j.Log4j2;
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

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GiftCertificateRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private GiftCertificate getGiftCertificateRowMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(resultSet.getLong(ID));
        giftCertificate.setName(resultSet.getString(NAME));
        giftCertificate.setDescription(resultSet.getString(DESCRIPTION));
        giftCertificate.setPrice(resultSet.getDouble(PRICE));
        giftCertificate.setDuration(resultSet.getInt(DURATION));
        giftCertificate.setCreateDate(resultSet.getTimestamp(CREATE_DATE).toLocalDateTime());
        giftCertificate.setLastUpdateDate(resultSet.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        log.info("Invoking method 'find all gift certificates'");
        return namedParameterJdbcTemplate.query(FIND_ALL_QUERY, this::getGiftCertificateRowMapper);
    }

    @Override
    public GiftCertificate findById(Long key) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", key);

        log.info("Invoking method 'find gift certificate by id'");
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_QUERY, parameterSource, this::getGiftCertificateRowMapper);
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        Timestamp createTimestamp = Timestamp.from(Instant.now());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", giftCertificate.getName());
        parameterSource.addValue("description", giftCertificate.getDescription());
        parameterSource.addValue("price", giftCertificate.getPrice());
        parameterSource.addValue("duration", giftCertificate.getDuration());
        parameterSource.addValue("create_date", createTimestamp);
        parameterSource.addValue("last_update_date", createTimestamp);

        namedParameterJdbcTemplate.update(CREATE_QUERY, parameterSource, keyHolder, new String[]{"id"});
        long createdGiftCertificateId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        log.info("Invoking method 'create gift certificate'");
        return findById(createdGiftCertificateId);
    }

    @Override
    public GiftCertificate updateById(Long id, GiftCertificate giftCertificate) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        Timestamp updateTimestamp = Timestamp.from(Instant.now());

        parameterSource.addValue("name", giftCertificate.getName());
        parameterSource.addValue("description", giftCertificate.getDescription());
        parameterSource.addValue("price", giftCertificate.getPrice());
        parameterSource.addValue("duration", giftCertificate.getDuration());
        parameterSource.addValue("last_update_date", updateTimestamp);
        parameterSource.addValue("id", id);

        namedParameterJdbcTemplate.update(UPDATE_QUERY, parameterSource);
        log.info("Invoking method 'update gift certificate'");
        return findById(id);
    }

    @Override
    public void deleteById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        log.info("Invoking method 'delete gift certificate'");
        namedParameterJdbcTemplate.update(DELETE_QUERY, parameterSource);
    }
}

