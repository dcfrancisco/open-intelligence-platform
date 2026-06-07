package com.oip.domain.rag;

import com.oip.domain.memory.MemoryService;
import java.util.List;

public interface ContextBuilder {
    String build(String question, List<MemoryService.MemoryEntryView> entries);
}
