package com.oip.domain.rag;

import com.oip.domain.ask.AskService;
import com.oip.domain.memory.MemoryService;
import java.util.List;

public interface CitationBuilder {
    List<AskService.SourceReference> build(List<MemoryService.MemoryEntryView> entries);
}
