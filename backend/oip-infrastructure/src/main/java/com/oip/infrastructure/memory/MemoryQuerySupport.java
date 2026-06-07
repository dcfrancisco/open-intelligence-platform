package com.oip.infrastructure.memory;

import com.oip.domain.memory.MemoryEntry;
import com.oip.domain.memory.MemoryService;
import com.oip.domain.memory.MemorySource;
import com.oip.domain.memory.MemoryTag;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class MemoryQuerySupport {

    private final MemoryEntryJpaRepository memoryEntryJpaRepository;
    private final MemorySourceJpaRepository memorySourceJpaRepository;
    private final MemoryTagJpaRepository memoryTagJpaRepository;

    public MemoryQuerySupport(
            MemoryEntryJpaRepository memoryEntryJpaRepository,
            MemorySourceJpaRepository memorySourceJpaRepository,
            MemoryTagJpaRepository memoryTagJpaRepository) {
        this.memoryEntryJpaRepository = memoryEntryJpaRepository;
        this.memorySourceJpaRepository = memorySourceJpaRepository;
        this.memoryTagJpaRepository = memoryTagJpaRepository;
    }

    public List<MemoryService.MemoryEntryView> search(UUID workspaceId, UUID collectionId, String query, int limit) {
        List<MemoryEntry> entries = memoryEntryJpaRepository.search(
                workspaceId,
                collectionId,
                query,
                PageRequest.of(0, Math.max(1, limit)));
        return mapEntries(entries);
    }

    public MemoryService.MemoryEntryView mapEntry(MemoryEntry entry) {
        return mapEntries(List.of(entry)).getFirst();
    }

    private List<MemoryService.MemoryEntryView> mapEntries(List<MemoryEntry> entries) {
        if (entries.isEmpty()) {
            return List.of();
        }

        Map<UUID, List<String>> tagsByEntry = memoryTagJpaRepository.findByEntryIdIn(entries.stream().map(MemoryEntry::getId).toList())
                .stream()
                .collect(Collectors.groupingBy(MemoryTag::getEntryId, Collectors.mapping(MemoryTag::getValue, Collectors.toList())));

        List<UUID> sourceIds = entries.stream()
                .map(MemoryEntry::getSourceId)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .toList();
        Map<UUID, MemorySource> sourcesById = memorySourceJpaRepository.findByIdIn(sourceIds)
                .stream()
                .collect(Collectors.toMap(MemorySource::getId, Function.identity()));

        return entries.stream()
                .map(entry -> {
                    MemorySource source = entry.getSourceId() == null ? null : sourcesById.get(entry.getSourceId());
                    return new MemoryService.MemoryEntryView(
                            entry.getId(),
                            entry.getWorkspaceId(),
                            entry.getCollectionId(),
                            entry.getSourceId(),
                            entry.getTitle(),
                            entry.getContent(),
                            tagsByEntry.getOrDefault(entry.getId(), Collections.emptyList()),
                            source == null ? null : source.getName(),
                            source == null ? null : source.getSourceType().name(),
                            source == null ? null : source.getUri(),
                            entry.getCreatedAt());
                })
                .toList();
    }
}
