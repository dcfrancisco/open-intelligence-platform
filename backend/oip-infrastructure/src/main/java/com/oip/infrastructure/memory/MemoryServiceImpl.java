package com.oip.infrastructure.memory;

import com.oip.domain.memory.MemoryCollection;
import com.oip.domain.memory.MemoryEntry;
import com.oip.domain.memory.MemoryService;
import com.oip.domain.memory.MemorySource;
import com.oip.domain.memory.MemoryTag;
import com.oip.domain.rag.EmbeddingService;
import com.oip.infrastructure.audit.AuditTrailService;
import com.oip.infrastructure.workspace.WorkspaceJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemoryServiceImpl implements MemoryService {

    private final WorkspaceJpaRepository workspaceJpaRepository;
    private final MemoryCollectionJpaRepository memoryCollectionJpaRepository;
    private final MemorySourceJpaRepository memorySourceJpaRepository;
    private final MemoryEntryJpaRepository memoryEntryJpaRepository;
    private final MemoryTagJpaRepository memoryTagJpaRepository;
    private final MemoryQuerySupport memoryQuerySupport;
    private final EmbeddingService embeddingService;
    private final AuditTrailService auditTrailService;

    public MemoryServiceImpl(
            WorkspaceJpaRepository workspaceJpaRepository,
            MemoryCollectionJpaRepository memoryCollectionJpaRepository,
            MemorySourceJpaRepository memorySourceJpaRepository,
            MemoryEntryJpaRepository memoryEntryJpaRepository,
            MemoryTagJpaRepository memoryTagJpaRepository,
            MemoryQuerySupport memoryQuerySupport,
            EmbeddingService embeddingService,
            AuditTrailService auditTrailService) {
        this.workspaceJpaRepository = workspaceJpaRepository;
        this.memoryCollectionJpaRepository = memoryCollectionJpaRepository;
        this.memorySourceJpaRepository = memorySourceJpaRepository;
        this.memoryEntryJpaRepository = memoryEntryJpaRepository;
        this.memoryTagJpaRepository = memoryTagJpaRepository;
        this.memoryQuerySupport = memoryQuerySupport;
        this.embeddingService = embeddingService;
        this.auditTrailService = auditTrailService;
    }

    @Override
    public MemoryCollectionView createCollection(CreateMemoryCollectionCommand command) {
        workspaceJpaRepository.findById(command.workspaceId())
                .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));
        MemoryCollection collection = memoryCollectionJpaRepository.save(new MemoryCollection(
                command.workspaceId(),
                command.name(),
                command.description()));
        auditTrailService.record(command.workspaceId(), "memory.collection.created", "MemoryCollection", collection.getId(), java.util.Map.of("name", collection.getName()));
        return new MemoryCollectionView(collection.getId(), collection.getWorkspaceId(), collection.getName(), collection.getDescription(), collection.getCreatedAt());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemoryCollectionView> listCollections(UUID workspaceId) {
        return memoryCollectionJpaRepository.findByWorkspaceIdOrderByCreatedAtAsc(workspaceId).stream()
                .map(collection -> new MemoryCollectionView(
                        collection.getId(),
                        collection.getWorkspaceId(),
                        collection.getName(),
                        collection.getDescription(),
                        collection.getCreatedAt()))
                .toList();
    }

    @Override
    public MemorySourceView uploadDocumentMetadata(UploadDocumentMetadataCommand command) {
        MemorySource source = memorySourceJpaRepository.save(new MemorySource(
                command.workspaceId(),
                command.collectionId(),
                command.sourceType(),
                command.name(),
                command.uri(),
                command.contentType()));
        auditTrailService.record(command.workspaceId(), "memory.source.created", "MemorySource", source.getId(), java.util.Map.of("name", source.getName()));
        return new MemorySourceView(
                source.getId(),
                source.getWorkspaceId(),
                source.getCollectionId(),
                source.getSourceType(),
                source.getName(),
                source.getUri(),
                source.getContentType(),
                source.getCreatedAt());
    }

    @Override
    public MemoryEntryView createMemoryEntry(CreateMemoryEntryCommand command) {
        embeddingService.embed(List.of(command.content()));
        MemoryEntry entry = memoryEntryJpaRepository.save(new MemoryEntry(
                command.workspaceId(),
                command.collectionId(),
                command.sourceId(),
                command.title(),
                command.content()));
        if (command.tags() != null) {
            command.tags().stream()
                    .filter(tag -> tag != null && !tag.isBlank())
                    .map(String::trim)
                    .map(tag -> new MemoryTag(entry.getId(), tag))
                    .forEach(memoryTagJpaRepository::save);
        }
        auditTrailService.record(command.workspaceId(), "memory.entry.created", "MemoryEntry", entry.getId(), java.util.Map.of("title", entry.getTitle()));
        return memoryQuerySupport.mapEntry(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemoryEntryView> searchMemory(SearchMemoryQuery query) {
        return memoryQuerySupport.search(query.workspaceId(), query.collectionId(), query.query(), query.limit());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MemoryEntryView> getMemoryEntry(UUID entryId) {
        return memoryEntryJpaRepository.findById(entryId).map(memoryQuerySupport::mapEntry);
    }

    @Override
    public void deleteMemoryEntry(UUID entryId) {
        MemoryEntry entry = memoryEntryJpaRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Memory entry not found"));
        memoryTagJpaRepository.deleteByEntryId(entryId);
        memoryEntryJpaRepository.delete(entry);
        auditTrailService.record(entry.getWorkspaceId(), "memory.entry.deleted", "MemoryEntry", entryId, java.util.Map.of("title", entry.getTitle()));
    }
}
