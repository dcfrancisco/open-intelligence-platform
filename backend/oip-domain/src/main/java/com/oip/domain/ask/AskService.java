package com.oip.domain.ask;

import com.oip.domain.memory.MemoryService;
import java.util.List;
import java.util.UUID;

public interface AskService {

    AskResult ask(AskCommand command);

    record AskCommand(UUID workspaceId, UUID collectionId, UUID conversationId, String question) {
    }

    record TimingMetrics(long retrievalMs, long generationMs, long totalMs) {
    }

    record AskResult(
            UUID conversationId,
            UUID promptId,
            UUID responseId,
            String answer,
            List<SourceReference> sources,
            List<MemoryService.MemoryEntryView> retrievedEntries,
            TimingMetrics timingMetrics) {
    }

    record SourceReference(UUID sourceId, String sourceName, String sourceType, String sourceUri) {
    }
}
