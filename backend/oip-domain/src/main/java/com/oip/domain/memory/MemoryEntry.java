package com.oip.domain.memory;

import com.oip.domain.shared.PersistableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "memory_entries")
public class MemoryEntry extends PersistableEntity {

    @Column(name = "workspace_id", nullable = false)
    private UUID workspaceId;

    @Column(name = "collection_id", nullable = false)
    private UUID collectionId;

    @Column(name = "source_id")
    private UUID sourceId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    protected MemoryEntry() {
    }

    public MemoryEntry(UUID workspaceId, UUID collectionId, UUID sourceId, String title, String content) {
        this.workspaceId = workspaceId;
        this.collectionId = collectionId;
        this.sourceId = sourceId;
        this.title = title;
        this.content = content;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public UUID getCollectionId() {
        return collectionId;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
