package com.oip.domain.rag;

import java.util.List;

public interface EmbeddingService {
    List<float[]> embed(List<String> texts);
}
