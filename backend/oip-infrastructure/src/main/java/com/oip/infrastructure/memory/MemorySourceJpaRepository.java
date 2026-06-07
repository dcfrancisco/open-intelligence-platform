package com.oip.infrastructure.memory;

import com.oip.domain.memory.MemorySource;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemorySourceJpaRepository extends JpaRepository<MemorySource, UUID> {
    List<MemorySource> findByIdIn(List<UUID> ids);
}
