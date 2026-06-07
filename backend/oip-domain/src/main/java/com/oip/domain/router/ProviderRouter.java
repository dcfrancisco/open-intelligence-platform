package com.oip.domain.router;

import java.util.UUID;

public interface ProviderRouter {

    ProviderSelection route(AskRoutingRequest request);

    record AskRoutingRequest(UUID workspaceId, String question) {
    }

    record ProviderSelection(UUID providerId, UUID modelId, String providerName, String baseUrl, String modelName) {
    }
}
