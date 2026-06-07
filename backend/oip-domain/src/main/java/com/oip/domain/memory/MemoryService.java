package com.oip.domain.memory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemoryService {

    MemoryCollectionView createCollection(CreateMemoryCollectionCommand command);

    List<MemoryCollectionView> listCollections(UUID workspaceId);

    MemorySourceView uploadDocumentMetadata(UploadDocumentMetadataCommand command);

    MemoryEntryView createMemoryEntry(CreateMemoryEntryCommand command);

    List<MemoryEntryView> searchMemory(SearchMemoryQuery query);

    Optional<MemoryEntryView> getMemoryEntry(UUID entryId);

    void deleteMemoryEntry(UUID entryId);

    record CreateMemoryCollectionCommand(UUID workspaceId, String name, String description) {
    }

    record UploadDocumentMetadataCommand(
            UUID workspaceId,
            UUID collectionId,
            MemorySourceType sourceType,
            String name,
            String uri,
            String contentType) {
    }

    record CreateMemoryEntryCommand(
            UUID workspaceId,
            UUID collectionId,
            UUID sourceId,
            String title,
            String content,
            List<String> tags) {
    }

    record SearchMemoryQuery(UUID workspaceId, UUID collectionId, String query, int limit) {
    }

    record MemoryCollectionView(UUID id, UUID workspaceId, String name, String description, OffsetDateTime createdAt) {
    }

    record MemorySourceView(
            UUID id,
            UUID workspaceId,
            UUID collectionId,
            MemorySourceType sourceType,
            String name,
            String uri,
            String contentType,
            OffsetDateTime createdAt) {
    }

    record MemoryEntryView(
            UUID id,
            UUID workspaceId,
            UUID collectionId,
            UUID sourceId,
            String title,
            String content,
            List<String> tags,
            String sourceName,
            String sourceType,
            String sourceUri,
            OffsetDateTime createdAt) {
    }
}
