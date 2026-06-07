package com.oip.domain.provider;

import java.util.List;
import java.util.UUID;

public interface ProviderService {

    ProviderView register(RegisterProviderCommand command);

    List<ProviderView> list();

    ProviderView enable(UUID providerId);

    ProviderView disable(UUID providerId);

    ProviderHealthView healthCheck(UUID providerId);

    record RegisterProviderCommand(
            UUID workspaceId,
            String name,
            ProviderType providerType,
            String baseUrl,
            String modelName,
            String modelVersion,
            Integer contextWindow,
            String capabilities,
            String costInformation,
            int routingPriority,
            boolean enabled) {
    }

    record ProviderView(
            UUID id,
            UUID workspaceId,
            String name,
            ProviderType providerType,
            String baseUrl,
            ProviderStatus status,
            List<ModelView> models) {
    }

    record ModelView(
            UUID id,
            String name,
            String version,
            Integer contextWindow,
            String capabilities,
            String costInformation,
            int routingPriority,
            ModelStatus status) {
    }

    record ProviderHealthView(UUID providerId, String providerName, boolean healthy, String detail) {
    }
}
