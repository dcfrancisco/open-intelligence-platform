package com.oip.infrastructure.rag;

import com.oip.domain.memory.MemoryService;
import com.oip.domain.rag.ChunkingService;
import com.oip.domain.rag.ContextBuilder;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultContextBuilder implements ContextBuilder {

    private final ChunkingService chunkingService;
    private final int maxContextCharacters;

    public DefaultContextBuilder(
            ChunkingService chunkingService,
            @Value("${oip.rag.max-context-characters}") int maxContextCharacters) {
        this.chunkingService = chunkingService;
        this.maxContextCharacters = maxContextCharacters;
    }

    @Override
    public String build(String question, List<MemoryService.MemoryEntryView> entries) {
        StringBuilder context = new StringBuilder();
        context.append("Answer the question using only the memory context below when possible.\n");
        context.append("Question: ").append(question).append("\n\n");
        context.append("Memory Context:\n");

        for (MemoryService.MemoryEntryView entry : entries) {
            for (String chunk : chunkingService.chunk(entry.content())) {
                String section = "- [" + entry.title() + "] " + chunk + "\n";
                if (context.length() + section.length() > maxContextCharacters) {
                    return context.toString();
                }
                context.append(section);
            }
        }
        return context.toString();
    }
}
