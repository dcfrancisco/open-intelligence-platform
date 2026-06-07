package com.oip.domain.conversation;

import com.oip.domain.shared.PersistableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "responses")
public class Response extends PersistableEntity {

    @Column(name = "prompt_id", nullable = false)
    private UUID promptId;

    @Column(name = "provider_name", nullable = false, length = 100)
    private String providerName;

    @Column(name = "model_name", nullable = false, length = 150)
    private String modelName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    @Column(name = "source_references", nullable = false, columnDefinition = "TEXT")
    private String sourceReferences;

    @Column(name = "retrieval_latency_ms", nullable = false)
    private long retrievalLatencyMs;

    @Column(name = "generation_latency_ms", nullable = false)
    private long generationLatencyMs;

    @Column(name = "total_latency_ms", nullable = false)
    private long totalLatencyMs;

    protected Response() {
    }

    public Response(
            UUID promptId,
            String providerName,
            String modelName,
            String answer,
            String sourceReferences,
            long retrievalLatencyMs,
            long generationLatencyMs,
            long totalLatencyMs) {
        this.promptId = promptId;
        this.providerName = providerName;
        this.modelName = modelName;
        this.answer = answer;
        this.sourceReferences = sourceReferences;
        this.retrievalLatencyMs = retrievalLatencyMs;
        this.generationLatencyMs = generationLatencyMs;
        this.totalLatencyMs = totalLatencyMs;
    }

    public UUID getPromptId() {
        return promptId;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getAnswer() {
        return answer;
    }

    public String getSourceReferences() {
        return sourceReferences;
    }

    public long getRetrievalLatencyMs() {
        return retrievalLatencyMs;
    }

    public long getGenerationLatencyMs() {
        return generationLatencyMs;
    }

    public long getTotalLatencyMs() {
        return totalLatencyMs;
    }
}
