package com.oip.domain.rag;

import java.util.List;

public interface ChunkingService {
    List<String> chunk(String content);
}
