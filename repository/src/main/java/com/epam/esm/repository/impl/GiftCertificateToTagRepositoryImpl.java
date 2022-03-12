package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateToTagRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Gift certificate to tag repository implementation.
 */
@Log4j2
@Repository
public class GiftCertificateToTagRepositoryImpl implements GiftCertificateToTagRepository {
    private static final String CREATE_LINK_QUERY = "insert into gift_certificate_to_tag (gift_certificate_id, tag_id) " +
            "values (?, ?)";
    private static final String FIND_LINK_QUERY
            = "select gift_certificate_id, tag_id from gift_certificate_to_tag " +
            "where gift_certificate_id = ? and tag_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateToTagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean createLink(Long giftCertificateId, Long tagId) {
        int createdRowLines = jdbcTemplate.update(CREATE_LINK_QUERY, giftCertificateId, tagId);
        return createdRowLines == 1;
    }

    @Override
    public Optional<GiftCertificateToTagRepository> findLink(Long giftCertificateId, Long tagId) {
        Optional<GiftCertificateToTagRepository> link;

        try {
            link = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_LINK_QUERY,
                    new BeanPropertyRowMapper<>(GiftCertificateToTagRepository.class), giftCertificateId, tagId));
        } catch (EmptyResultDataAccessException ex) {
            link = Optional.empty();
        }

        return link;
    }
}
