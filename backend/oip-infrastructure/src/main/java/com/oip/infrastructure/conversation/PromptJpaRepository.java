package com.oip.infrastructure.conversation;

import com.oip.domain.conversation.Prompt;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptJpaRepository extends JpaRepository<Prompt, UUID> {
}
