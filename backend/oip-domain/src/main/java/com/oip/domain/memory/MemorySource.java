package com.oip.domain.memory;

import com.oip.domain.shared.PersistableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "memory_sources")
public class MemorySource extends PersistableEntity {

    @Column(name = "workspace_id", nullable = false)
    private UUID workspaceId;

    @Column(name = "collection_id", nullable = false)
    private UUID collectionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 50)
    private MemorySourceType sourceType;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 1000)
    private String uri;

    @Column(name = "content_type", length = 150)
    private String contentType;

    protected MemorySource() {
    }

    public MemorySource(UUID workspaceId, UUID collectionId, MemorySourceType sourceType, String name, String uri, String contentType) {
        this.workspaceId = workspaceId;
        this.collectionId = collectionId;
        this.sourceType = sourceType;
        this.name = name;
        this.uri = uri;
        this.contentType = contentType;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public UUID getCollectionId() {
        return collectionId;
    }

    public MemorySourceType getSourceType() {
        return sourceType;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getContentType() {
        return contentType;
    }
}
