package com.oip.infrastructure.provider;

import com.oip.domain.provider.Model;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelJpaRepository extends JpaRepository<Model, UUID> {
    List<Model> findByProviderIdOrderByRoutingPriorityAscCreatedAtAsc(UUID providerId);
}
