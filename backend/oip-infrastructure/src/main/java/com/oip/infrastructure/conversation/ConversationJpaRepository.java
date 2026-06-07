package com.oip.infrastructure.conversation;

import com.oip.domain.conversation.Conversation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationJpaRepository extends JpaRepository<Conversation, UUID> {
}
