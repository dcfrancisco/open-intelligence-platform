package com.oip.infrastructure.health;

import com.oip.domain.provider.ProviderService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProviderHealthService implements com.oip.domain.provider.ProviderHealthService {

    private final ProviderService providerService;

    public ProviderHealthService(ProviderService providerService) {
        this.providerService = providerService;
    }

    @Override
    public List<ProviderService.ProviderHealthView> allProviderHealth() {
        return providerService.list().stream()
                .map(provider -> providerService.healthCheck(provider.id()))
                .toList();
    }
}
