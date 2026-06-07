package com.oip.infrastructure.audit;

import com.oip.domain.audit.AuditEvent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEventJpaRepository extends JpaRepository<AuditEvent, UUID> {
}
