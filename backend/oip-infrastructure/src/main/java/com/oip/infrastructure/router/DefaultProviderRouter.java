package com.oip.infrastructure.router;

import com.oip.domain.provider.Model;
import com.oip.domain.provider.ModelStatus;
import com.oip.domain.provider.Provider;
import com.oip.domain.provider.ProviderStatus;
import com.oip.domain.router.ProviderRouter;
import com.oip.infrastructure.provider.ModelJpaRepository;
import com.oip.infrastructure.provider.ProviderJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultProviderRouter implements ProviderRouter {

    private final ProviderJpaRepository providerJpaRepository;
    private final ModelJpaRepository modelJpaRepository;

    public DefaultProviderRouter(ProviderJpaRepository providerJpaRepository, ModelJpaRepository modelJpaRepository) {
        this.providerJpaRepository = providerJpaRepository;
        this.modelJpaRepository = modelJpaRepository;
    }

    @Override
    public ProviderSelection route(AskRoutingRequest request) {
        Provider provider = providerJpaRepository.findByStatusOrderByCreatedAtAsc(ProviderStatus.ENABLED)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No enabled provider is configured"));

        Model model = modelJpaRepository.findByProviderIdOrderByRoutingPriorityAscCreatedAtAsc(provider.getId())
                .stream()
                .filter(candidate -> candidate.getStatus() == ModelStatus.ENABLED)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No enabled model is configured for provider " + provider.getName()));

        return new ProviderSelection(provider.getId(), model.getId(), provider.getName(), provider.getBaseUrl(), model.getName());
    }
}
