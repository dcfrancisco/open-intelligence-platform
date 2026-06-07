package com.oip.domain.audit;

import com.oip.domain.shared.PersistableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "audit_events")
public class AuditEvent extends PersistableEntity {

    @Column(name = "workspace_id")
    private UUID workspaceId;

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType;

    @Column(name = "entity_id")
    private UUID entityId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String details;

    protected AuditEvent() {
    }

    public AuditEvent(UUID workspaceId, String eventType, String entityType, UUID entityId, String details) {
        this.workspaceId = workspaceId;
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.details = details;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEntityType() {
        return entityType;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public String getDetails() {
        return details;
    }
}
