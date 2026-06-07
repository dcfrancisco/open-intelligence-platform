package com.oip.infrastructure.memory;

import com.oip.domain.memory.MemoryEntry;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemoryEntryJpaRepository extends JpaRepository<MemoryEntry, UUID> {

    @Query("""
        select e from MemoryEntry e
        where e.workspaceId = :workspaceId
          and (:collectionId is null or e.collectionId = :collectionId)
          and (
            lower(e.title) like lower(concat('%', :query, '%'))
            or lower(e.content) like lower(concat('%', :query, '%'))
          )
        order by e.updatedAt desc
        """)
    List<MemoryEntry> search(
            @Param("workspaceId") UUID workspaceId,
            @Param("collectionId") UUID collectionId,
            @Param("query") String query,
            Pageable pageable);

    Optional<MemoryEntry> findByIdAndWorkspaceId(UUID id, UUID workspaceId);
}
