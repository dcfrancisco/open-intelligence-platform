package com.oip.infrastructure.provider;

import com.oip.domain.provider.Model;
import com.oip.domain.provider.ModelStatus;
import com.oip.domain.provider.Provider;
import com.oip.domain.provider.ProviderStatus;
import com.oip.domain.provider.ProviderType;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class OllamaBootstrap implements ApplicationRunner {

    private final OllamaProperties ollamaProperties;
    private final ProviderJpaRepository providerJpaRepository;
    private final ModelJpaRepository modelJpaRepository;

    public OllamaBootstrap(
            OllamaProperties ollamaProperties,
            ProviderJpaRepository providerJpaRepository,
            ModelJpaRepository modelJpaRepository) {
        this.ollamaProperties = ollamaProperties;
        this.providerJpaRepository = providerJpaRepository;
        this.modelJpaRepository = modelJpaRepository;
    }

    @Override
    @Transactional
    public void run(org.springframework.boot.ApplicationArguments args) {
        if (!ollamaProperties.isEnabled()) {
            return;
        }

        Provider provider = providerJpaRepository.findFirstByProviderTypeOrderByCreatedAtAsc(ProviderType.OLLAMA)
                .orElseGet(() -> providerJpaRepository.save(new Provider(
                        null,
                        ollamaProperties.getProviderName(),
                        ProviderType.OLLAMA,
                        ollamaProperties.getBaseUrl(),
                        ProviderStatus.ENABLED)));

        boolean modelExists = modelJpaRepository.findByProviderIdOrderByRoutingPriorityAscCreatedAtAsc(provider.getId())
                .stream()
                .anyMatch(existing -> existing.getName().equalsIgnoreCase(ollamaProperties.getModelName()));

        if (!modelExists) {
            modelJpaRepository.save(new Model(
                    provider.getId(),
                    ollamaProperties.getModelName(),
                    null,
                    8192,
                    "chat,grounded-response",
                    "local",
                    100,
                    ModelStatus.ENABLED));
        }
    }
}
