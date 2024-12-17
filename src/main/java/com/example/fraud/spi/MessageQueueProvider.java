package com.example.fraud.spi;

import java.util.Optional;
import java.util.ServiceLoader;

public interface MessageQueueProvider {

    void connect();

    String getQueueName();

    static Optional<MessageQueueProvider> load(String providerName) {
        ServiceLoader<MessageQueueProvider> loader = ServiceLoader.load(MessageQueueProvider.class);
        for (MessageQueueProvider provider : loader) {
            if (provider.getClass().getSimpleName().equalsIgnoreCase(providerName)) {
                return Optional.of(provider);
            }
        }
        return Optional.empty();
    }
}
