package com.epam.esm.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that provides JWT token data
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwtconfig")
public class JwtConfig {
    private String accessSecret;

    private String refreshSecret;

    private Long accessExpiration;

    private Long refreshExpiration;
}
