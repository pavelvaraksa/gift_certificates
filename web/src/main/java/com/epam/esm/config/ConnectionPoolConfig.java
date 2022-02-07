package com.epam.esm.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class ConnectionPoolConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Profile({"dev", "prod"})
    @Bean
    public DataSource hikariDatasource(DatabaseConfig databaseConfig) {
        HikariDataSource hikariDataSource = new HikariDataSource();

        hikariDataSource.setDriverClassName(databaseConfig.getDriverName());
        hikariDataSource.setJdbcUrl(databaseConfig.getUrl());
        hikariDataSource.setUsername(databaseConfig.getLogin());
        hikariDataSource.setPassword(databaseConfig.getPassword());
        hikariDataSource.setMaximumPoolSize(10);

        return hikariDataSource;
    }
}

