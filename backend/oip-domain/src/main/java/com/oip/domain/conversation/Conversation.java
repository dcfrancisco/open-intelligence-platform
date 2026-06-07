package com.oip.domain.conversation;

import com.oip.domain.shared.PersistableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "conversations")
public class Conversation extends PersistableEntity {

    @Column(name = "workspace_id", nullable = false)
    private UUID workspaceId;

    protected Conversation() {
    }

    public Conversation(UUID workspaceId) {
        this.workspaceId = workspaceId;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }
}
