package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateToTag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class GiftCertificateToTagImpl implements GiftCertificateToTag {
    private static final String FIND_ALL_BY_GIFT_CERTIFICATE_ID_QUERY
            = "select gift_certificate_id, tag_id from gift_certificate_to_tag where gift_certificate_id = ?";
    private static final String CREATE_LINK_QUERY = "insert into gift_certificate_to_tag (gift_certificate_id, tag_id) " +
            "values (?, ?)";
    private static final String DELETE_LINK_QUERY = "delete from gift_certificate_to_tag " +
            "where gift_certificate_id = ? and tag_id = ?";
    private static final String FIND_LINK_QUERY
            = "select gift_certificate_id, tag_id from gift_certificate_to_tag " +
            "where gift_certificate_id = ? and tag_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateToTagImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean createLink(Long giftCertificateId, Long tagId) {
        int createdRowLines = jdbcTemplate.update(CREATE_LINK_QUERY, giftCertificateId, tagId);
        return createdRowLines == 1;
    }

    @Override
    public Optional<GiftCertificateToTag> findLink(Long giftCertificateId, Long tagId) {
        Optional<GiftCertificateToTag> link;

        try {
            link = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_LINK_QUERY,
                    new BeanPropertyRowMapper<>(GiftCertificateToTag.class), giftCertificateId, tagId));
        } catch (EmptyResultDataAccessException ex) {
            link = Optional.empty();
        }

        return link;
    }

    @Override
    public List<GiftCertificateToTag> findAllByGiftCertificateId(Long id) {
        return jdbcTemplate.query(FIND_ALL_BY_GIFT_CERTIFICATE_ID_QUERY, new BeanPropertyRowMapper<>(), id);
    }

    @Override
    public boolean deleteLink(Long giftCertificateId, Long tagId) {
        int createdRowLines = jdbcTemplate.update(DELETE_LINK_QUERY, giftCertificateId, tagId);
        return createdRowLines == 1;
    }
}
