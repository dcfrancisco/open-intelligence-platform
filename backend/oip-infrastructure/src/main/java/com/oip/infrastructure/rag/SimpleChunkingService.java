package com.oip.infrastructure.rag;

import com.oip.domain.rag.ChunkingService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SimpleChunkingService implements ChunkingService {

    private static final int MAX_CHARS = 800;

    @Override
    public List<String> chunk(String content) {
        String normalized = content == null ? "" : content.trim().replace("\r", "");
        if (normalized.isBlank()) {
            return List.of();
        }

        List<String> chunks = new ArrayList<>();
        String[] paragraphs = normalized.split("\n\n+");
        StringBuilder current = new StringBuilder();
        for (String paragraph : paragraphs) {
            if (current.length() + paragraph.length() + 2 > MAX_CHARS && current.length() > 0) {
                chunks.add(current.toString().trim());
                current.setLength(0);
            }
            if (current.length() > 0) {
                current.append("\n\n");
            }
            current.append(paragraph.trim());
        }
        if (current.length() > 0) {
            chunks.add(current.toString().trim());
        }
        return chunks;
    }
}
