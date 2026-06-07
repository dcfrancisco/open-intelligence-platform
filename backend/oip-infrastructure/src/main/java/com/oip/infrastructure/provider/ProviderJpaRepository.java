package com.oip.infrastructure.provider;

import com.oip.domain.provider.Provider;
import com.oip.domain.provider.ProviderStatus;
import com.oip.domain.provider.ProviderType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderJpaRepository extends JpaRepository<Provider, UUID> {
    List<Provider> findAllByOrderByCreatedAtAsc();
    List<Provider> findByStatusOrderByCreatedAtAsc(ProviderStatus status);
    Optional<Provider> findFirstByProviderTypeOrderByCreatedAtAsc(ProviderType providerType);
}
