package com.oip.infrastructure.memory;

import com.oip.domain.memory.MemoryCollection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryCollectionJpaRepository extends JpaRepository<MemoryCollection, UUID> {
    List<MemoryCollection> findByWorkspaceIdOrderByCreatedAtAsc(UUID workspaceId);
}
