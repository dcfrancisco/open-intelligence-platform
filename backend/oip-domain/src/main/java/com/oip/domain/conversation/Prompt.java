package com.oip.domain.conversation;

import com.oip.domain.shared.PersistableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "prompts")
public class Prompt extends PersistableEntity {

    @Column(name = "conversation_id", nullable = false)
    private UUID conversationId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    protected Prompt() {
    }

    public Prompt(UUID conversationId, String content) {
        this.conversationId = conversationId;
        this.content = content;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public String getContent() {
        return content;
    }
}
