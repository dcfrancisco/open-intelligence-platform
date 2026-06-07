package com.oip.infrastructure.memory;

import com.oip.domain.memory.MemoryTag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryTagJpaRepository extends JpaRepository<MemoryTag, UUID> {
    List<MemoryTag> findByEntryIdIn(List<UUID> entryIds);
    void deleteByEntryId(UUID entryId);
}
