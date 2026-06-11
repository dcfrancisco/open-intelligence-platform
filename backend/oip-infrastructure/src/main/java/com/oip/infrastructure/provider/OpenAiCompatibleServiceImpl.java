package com.oip.infrastructure.provider;

import com.oip.domain.provider.ModelStatus;
import com.oip.domain.provider.OpenAiCompatibleService;
import com.oip.domain.provider.ProviderService;
import com.oip.domain.provider.ProviderStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OpenAiCompatibleServiceImpl implements OpenAiCompatibleService {

    private final ProviderService providerService;
    private final OllamaClient ollamaClient;

    public OpenAiCompatibleServiceImpl(ProviderService providerService, OllamaClient ollamaClient) {
        this.providerService = providerService;
        this.ollamaClient = ollamaClient;
    }

    @Override
    public List<AvailableModel> listModels() {
        return providerService.list().stream()
                .filter(provider -> provider.status() == ProviderStatus.ENABLED)
                .flatMap(provider -> provider.models().stream()
                        .filter(model -> model.status() == ModelStatus.ENABLED)
                        .map(model -> new AvailableModel(
                                model.name(),
                                provider.name(),
                                provider.providerType().name(),
                                model.contextWindow(),
                                model.capabilities(),
                                model.costInformation())))
                .toList();
    }

    @Override
    public ChatCompletionResult chatCompletion(ChatCompletionCommand command) {
        RoutedModel routedModel = resolveModel(command.requestedModel());
        List<OllamaModels.OllamaMessage> messages = command.messages().stream()
                .map(message -> new OllamaModels.OllamaMessage(message.role(), message.content()))
                .toList();
        String content = ollamaClient.chat(routedModel.baseUrl(), routedModel.modelName(), messages);
        int promptTokens = estimateTokens(command.messages().stream()
                .map(ChatMessage::content)
                .toList());
        int completionTokens = estimateTokens(List.of(content));
        return new ChatCompletionResult(
                "chatcmpl-" + UUID.randomUUID(),
                routedModel.modelName(),
                content,
                "stop",
                promptTokens,
                completionTokens,
                promptTokens + completionTokens);
    }

    private RoutedModel resolveModel(String requestedModel) {
        List<RoutedModel> availableModels = providerService.list().stream()
                .filter(provider -> provider.status() == ProviderStatus.ENABLED)
                .flatMap(provider -> provider.models().stream()
                        .filter(model -> model.status() == ModelStatus.ENABLED)
                        .map(model -> new RoutedModel(provider.baseUrl(), model.name(), model.routingPriority())))
                .sorted((left, right) -> Integer.compare(left.routingPriority(), right.routingPriority()))
                .toList();

        if (availableModels.isEmpty()) {
            throw new IllegalStateException("No enabled provider and model are configured");
        }

        if (requestedModel == null || requestedModel.isBlank()) {
            return availableModels.getFirst();
        }

        return availableModels.stream()
                .filter(model -> model.modelName().equalsIgnoreCase(requestedModel))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Requested model is not available: " + requestedModel));
    }

    private int estimateTokens(List<String> segments) {
        return segments.stream()
                .filter(segment -> segment != null && !segment.isBlank())
                .mapToInt(segment -> Math.max(1, segment.length() / 4))
                .sum();
    }

    private record RoutedModel(String baseUrl, String modelName, int routingPriority) {
    }
}
