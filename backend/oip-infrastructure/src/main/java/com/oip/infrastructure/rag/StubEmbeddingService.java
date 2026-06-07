package com.oip.infrastructure.rag;

import com.oip.domain.rag.EmbeddingService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StubEmbeddingService implements EmbeddingService {

    @Override
    public List<float[]> embed(List<String> texts) {
        List<float[]> embeddings = new ArrayList<>();
        for (int i = 0; i < texts.size(); i++) {
            embeddings.add(new float[0]);
        }
        return embeddings;
    }
}
