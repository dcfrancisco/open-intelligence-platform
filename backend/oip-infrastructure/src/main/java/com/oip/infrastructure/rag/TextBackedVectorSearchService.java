package com.oip.infrastructure.rag;

import com.oip.domain.memory.MemoryService;
import com.oip.domain.rag.VectorSearchService;
import com.oip.infrastructure.memory.MemoryQuerySupport;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TextBackedVectorSearchService implements VectorSearchService {

    private final MemoryQuerySupport memoryQuerySupport;

    public TextBackedVectorSearchService(MemoryQuerySupport memoryQuerySupport) {
        this.memoryQuerySupport = memoryQuerySupport;
    }

    @Override
    public List<MemoryService.MemoryEntryView> search(UUID workspaceId, UUID collectionId, String query, int limit) {
        return memoryQuerySupport.search(workspaceId, collectionId, query, limit);
    }
}
