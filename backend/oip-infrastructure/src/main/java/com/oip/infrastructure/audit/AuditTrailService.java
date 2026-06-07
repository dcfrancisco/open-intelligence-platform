package com.oip.infrastructure.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oip.domain.audit.AuditEvent;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuditTrailService {

    private final AuditEventJpaRepository auditEventJpaRepository;
    private final ObjectMapper objectMapper;

    public AuditTrailService(AuditEventJpaRepository auditEventJpaRepository, ObjectMapper objectMapper) {
        this.auditEventJpaRepository = auditEventJpaRepository;
        this.objectMapper = objectMapper;
    }

    public void record(UUID workspaceId, String eventType, String entityType, UUID entityId, Map<String, Object> details) {
        auditEventJpaRepository.save(new AuditEvent(workspaceId, eventType, entityType, entityId, serialize(details)));
    }

    private String serialize(Map<String, Object> details) {
        try {
            return objectMapper.writeValueAsString(details);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize audit event details", ex);
        }
    }
}
