package com.epam.esm.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    @Value("#{environment['spring.profiles.active'] == 'prod' ? ${db.prod} : ${db.dev}}")
    private String databaseProperties;

    @Value("${driverName}")
    private String driverName;

    @Value("${url}")
    private String url;

    @Value("${login}")
    private String login;

    @Value("${password}")
    private String password;
}

