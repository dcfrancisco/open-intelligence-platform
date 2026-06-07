package com.oip.infrastructure.health;

import com.oip.infrastructure.provider.OllamaClient;
import com.oip.infrastructure.provider.OllamaProperties;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class OllamaHealthIndicator implements HealthIndicator {

    private final OllamaClient ollamaClient;
    private final OllamaProperties ollamaProperties;

    public OllamaHealthIndicator(OllamaClient ollamaClient, OllamaProperties ollamaProperties) {
        this.ollamaClient = ollamaClient;
        this.ollamaProperties = ollamaProperties;
    }

    @Override
    public Health health() {
        boolean healthy = ollamaClient.isHealthy(ollamaProperties.getBaseUrl());
        return healthy
                ? Health.up().withDetail("provider", "ollama").build()
                : Health.down().withDetail("provider", "ollama").build();
    }
}
