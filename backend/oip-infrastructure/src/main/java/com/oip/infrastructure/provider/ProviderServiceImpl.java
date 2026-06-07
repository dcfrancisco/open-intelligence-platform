package com.oip.infrastructure.provider;

import com.oip.domain.provider.Model;
import com.oip.domain.provider.ModelStatus;
import com.oip.domain.provider.Provider;
import com.oip.domain.provider.ProviderService;
import com.oip.domain.provider.ProviderStatus;
import com.oip.infrastructure.audit.AuditTrailService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProviderServiceImpl implements ProviderService {

    private final ProviderJpaRepository providerJpaRepository;
    private final ModelJpaRepository modelJpaRepository;
    private final OllamaClient ollamaClient;
    private final AuditTrailService auditTrailService;

    public ProviderServiceImpl(
            ProviderJpaRepository providerJpaRepository,
            ModelJpaRepository modelJpaRepository,
            OllamaClient ollamaClient,
            AuditTrailService auditTrailService) {
        this.providerJpaRepository = providerJpaRepository;
        this.modelJpaRepository = modelJpaRepository;
        this.ollamaClient = ollamaClient;
        this.auditTrailService = auditTrailService;
    }

    @Override
    public ProviderView register(RegisterProviderCommand command) {
        ProviderStatus status = command.enabled() ? ProviderStatus.ENABLED : ProviderStatus.DISABLED;
        Provider provider = providerJpaRepository.save(new Provider(
                command.workspaceId(),
                command.name(),
                command.providerType(),
                command.baseUrl(),
                status));
        Model model = modelJpaRepository.save(new Model(
                provider.getId(),
                command.modelName(),
                command.modelVersion(),
                command.contextWindow(),
                command.capabilities(),
                command.costInformation(),
                command.routingPriority(),
                command.enabled() ? ModelStatus.ENABLED : ModelStatus.DISABLED));
        auditTrailService.record(command.workspaceId(), "provider.registered", "Provider", provider.getId(), Map.of("name", provider.getName()));
        return toView(provider, List.of(model));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProviderView> list() {
        return providerJpaRepository.findAllByOrderByCreatedAtAsc().stream()
                .map(provider -> toView(provider, modelJpaRepository.findByProviderIdOrderByRoutingPriorityAscCreatedAtAsc(provider.getId())))
                .toList();
    }

    @Override
    public ProviderView enable(UUID providerId) {
        Provider provider = getProvider(providerId);
        provider.enable();
        Provider saved = providerJpaRepository.save(provider);
        auditTrailService.record(provider.getWorkspaceId(), "provider.enabled", "Provider", providerId, Map.of("name", provider.getName()));
        return toView(saved, modelJpaRepository.findByProviderIdOrderByRoutingPriorityAscCreatedAtAsc(providerId));
    }

    @Override
    public ProviderView disable(UUID providerId) {
        Provider provider = getProvider(providerId);
        provider.disable();
        Provider saved = providerJpaRepository.save(provider);
        auditTrailService.record(provider.getWorkspaceId(), "provider.disabled", "Provider", providerId, Map.of("name", provider.getName()));
        return toView(saved, modelJpaRepository.findByProviderIdOrderByRoutingPriorityAscCreatedAtAsc(providerId));
    }

    @Override
    public ProviderHealthView healthCheck(UUID providerId) {
        Provider provider = getProvider(providerId);
        boolean healthy = ollamaClient.isHealthy(provider.getBaseUrl());
        if (healthy && provider.getStatus() == ProviderStatus.UNHEALTHY) {
            provider.enable();
            providerJpaRepository.save(provider);
        } else if (!healthy && provider.getStatus() == ProviderStatus.ENABLED) {
            provider.markUnhealthy();
            providerJpaRepository.save(provider);
        }
        return new ProviderHealthView(provider.getId(), provider.getName(), healthy, healthy ? "healthy" : "unreachable");
    }

    private Provider getProvider(UUID providerId) {
        return providerJpaRepository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Provider not found"));
    }

    private ProviderView toView(Provider provider, List<Model> models) {
        return new ProviderView(
                provider.getId(),
                provider.getWorkspaceId(),
                provider.getName(),
                provider.getProviderType(),
                provider.getBaseUrl(),
                provider.getStatus(),
                models.stream()
                        .map(model -> new ModelView(
                                model.getId(),
                                model.getName(),
                                model.getVersion(),
                                model.getContextWindow(),
                                model.getCapabilities(),
                                model.getCostInformation(),
                                model.getRoutingPriority(),
                                model.getStatus()))
                        .toList());
    }
}
