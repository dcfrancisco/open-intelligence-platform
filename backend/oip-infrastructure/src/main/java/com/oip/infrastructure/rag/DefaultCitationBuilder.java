package com.oip.infrastructure.rag;

import com.oip.domain.ask.AskService;
import com.oip.domain.memory.MemoryService;
import com.oip.domain.rag.CitationBuilder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DefaultCitationBuilder implements CitationBuilder {

    @Override
    public List<AskService.SourceReference> build(List<MemoryService.MemoryEntryView> entries) {
        Map<UUID, AskService.SourceReference> deduplicated = new LinkedHashMap<>();
        for (MemoryService.MemoryEntryView entry : entries) {
            if (entry.sourceId() != null && !deduplicated.containsKey(entry.sourceId())) {
                deduplicated.put(entry.sourceId(), new AskService.SourceReference(
                        entry.sourceId(),
                        entry.sourceName(),
                        entry.sourceType(),
                        entry.sourceUri()));
            }
        }
        return new ArrayList<>(deduplicated.values());
    }
}
