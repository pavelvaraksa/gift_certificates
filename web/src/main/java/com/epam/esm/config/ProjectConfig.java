package com.epam.esm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Project configuration.
 */
public class ProjectConfig {
    @Configuration
    @PropertySource({"classpath:application.properties", "classpath:application-${spring.profiles.active}.properties"})
    static class NonDefaultProperties {
    }
}
