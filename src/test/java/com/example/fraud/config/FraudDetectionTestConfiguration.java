package com.example.fraud.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Test configuration for providing test-specific beans.
 */
@TestConfiguration
public class FraudDetectionTestConfiguration {

    /**
     * Provides an ObjectMapper bean for use in tests.
     *
     * @return a configured ObjectMapper instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
