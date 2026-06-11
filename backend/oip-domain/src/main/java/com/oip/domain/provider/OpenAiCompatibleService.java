package com.oip.domain.provider;

import java.util.List;

public interface OpenAiCompatibleService {

    List<AvailableModel> listModels();

    ChatCompletionResult chatCompletion(ChatCompletionCommand command);

    record AvailableModel(
            String id,
            String providerName,
            String providerType,
            Integer contextWindow,
            String capabilities,
            String costInformation) {
    }

    record ChatCompletionCommand(
            String requestedModel,
            List<ChatMessage> messages) {
    }

    record ChatMessage(String role, String content) {
    }

    record ChatCompletionResult(
            String completionId,
            String model,
            String content,
            String finishReason,
            int promptTokens,
            int completionTokens,
            int totalTokens) {
    }
}
