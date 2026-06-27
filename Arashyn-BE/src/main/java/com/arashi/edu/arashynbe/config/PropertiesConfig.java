package com.arashi.edu.arashynbe.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

/**
 * Enable scanning of all classes annotated with @ConfigurationProperties.
 *
 * This configuration allows Spring Boot to automatically detect and register
 * strongly-typed property classes located in the project.
 *
 * Instead of using @Value("${...}") throughout the application,
 * all external configurations should be mapped into dedicated property classes.
 */
@Configuration
@ConfigurationPropertiesScan(
        basePackages = "com.arashi.edu.arashynbe.config.properties"
)
public class PropertiesConfig {
}