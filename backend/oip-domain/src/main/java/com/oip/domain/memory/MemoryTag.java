package com.oip.domain.memory;

import com.oip.domain.shared.PersistableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "memory_tags")
public class MemoryTag extends PersistableEntity {

    @Column(name = "entry_id", nullable = false)
    private UUID entryId;

    @Column(nullable = false, length = 100)
    private String value;

    protected MemoryTag() {
    }

    public MemoryTag(UUID entryId, String value) {
        this.entryId = entryId;
        this.value = value;
    }

    public UUID getEntryId() {
        return entryId;
    }

    public String getValue() {
        return value;
    }
}
