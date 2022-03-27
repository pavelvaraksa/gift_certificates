package com.epam.esm.security.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@EqualsAndHashCode
@Configuration
@ConfigurationProperties("jwtconfig")
public class JwtConfig {
    private String secret;

    private Long expiration;
}
