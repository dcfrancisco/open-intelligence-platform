package com.oip.infrastructure.instruction;

import com.oip.domain.provider.OpenAiCompatibleService;
import com.oip.infrastructure.provider.OllamaModels;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OipInstructionProfile {

    private final OipPersonalityProperties properties;

    public OipInstructionProfile(OipPersonalityProperties properties) {
        this.properties = properties;
    }

    public String systemInstruction() {
        return properties.getSystemInstruction();
    }

    public List<OllamaModels.OllamaMessage> askMessages(String context) {
        return List.of(
                new OllamaModels.OllamaMessage("system", systemInstruction()),
                new OllamaModels.OllamaMessage("user", context));
    }

    public List<OllamaModels.OllamaMessage> assistantMessages(List<OpenAiCompatibleService.ChatMessage> incomingMessages) {
        List<OllamaModels.OllamaMessage> messages = new ArrayList<>();
        messages.add(new OllamaModels.OllamaMessage("system", systemInstruction()));
        incomingMessages.stream()
                .map(message -> new OllamaModels.OllamaMessage(message.role(), message.content()))
                .forEach(messages::add);
        return List.copyOf(messages);
    }
}
