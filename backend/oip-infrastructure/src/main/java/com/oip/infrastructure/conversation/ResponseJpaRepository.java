package com.oip.infrastructure.conversation;

import com.oip.domain.conversation.Response;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseJpaRepository extends JpaRepository<Response, UUID> {
}
