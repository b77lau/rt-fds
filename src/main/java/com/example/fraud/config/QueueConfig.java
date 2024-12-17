package com.example.fraud.config;

import com.example.fraud.queue.spi.QueueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Value("${message.queue.provider}")
    private String providerName;

    /**
     * MessageQueueProvider Bean - Loads provider dynamically using SPI
     */
    @Bean
    public QueueService messageQueueProvider() {
        return QueueService.load(providerName)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported queue provider: " + providerName));
    }
}
