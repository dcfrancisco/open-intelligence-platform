package com.oip.domain.rag;

import com.oip.domain.memory.MemoryService;
import java.util.List;
import java.util.UUID;

public interface VectorSearchService {
    List<MemoryService.MemoryEntryView> search(UUID workspaceId, UUID collectionId, String query, int limit);
}
