package com.oip.infrastructure.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

public final class OllamaModels {

    private OllamaModels() {
    }

    public record OllamaMessage(String role, String content) {
    }

    public record OllamaChatRequest(String model, List<OllamaMessage> messages, boolean stream) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OllamaChatResponse(OllamaMessage message) {
    }
}
