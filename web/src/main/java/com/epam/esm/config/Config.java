package com.epam.esm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

public class Config {
    @Profile("default")
    @PropertySource("classpath:application.properties")
    @Configuration
    static class DefaultProperties {
    }

    @Configuration
    @Profile("!default")
    @PropertySource({"classpath:application.properties", "classpath:application-${spring.profiles.active}.properties"})
    static class NonDefaultProperties {
    }
}
