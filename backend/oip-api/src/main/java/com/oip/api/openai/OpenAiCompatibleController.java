package com.oip.api.openai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oip.domain.provider.OpenAiCompatibleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Validated
@Tag(name = "OpenAI Compatible API")
public class OpenAiCompatibleController {

    private final OpenAiCompatibleService openAiCompatibleService;
    private final ObjectMapper objectMapper;

    public OpenAiCompatibleController(OpenAiCompatibleService openAiCompatibleService, ObjectMapper objectMapper) {
        this.openAiCompatibleService = openAiCompatibleService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/models")
    @Operation(summary = "List available models using the OpenAI-compatible schema")
    public ListModelsResponse listModels() {
        long created = Instant.now().getEpochSecond();
        List<ModelResponse> models = openAiCompatibleService.listModels().stream()
                .map(model -> new ModelResponse(
                        model.id(),
                        "model",
                        created,
                        model.providerName(),
                        model.contextWindow(),
                        model.capabilities(),
                        model.costInformation()))
                .toList();
        return new ListModelsResponse("list", models);
    }

    @PostMapping("/chat/completions")
    @Operation(summary = "Create a chat completion using the OpenAI-compatible schema")
    public void chatCompletions(@Valid @RequestBody ChatCompletionRequest request, HttpServletResponse response) throws IOException {
        OpenAiCompatibleService.ChatCompletionResult result = openAiCompatibleService.chatCompletion(
                new OpenAiCompatibleService.ChatCompletionCommand(
                        request.model(),
                        request.messages().stream()
                                .map(message -> new OpenAiCompatibleService.ChatMessage(message.role(), message.content()))
                                .toList()));

        if (Boolean.TRUE.equals(request.stream())) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
            writeStreamingCompletion(result, response);
            return;
        }

        long created = Instant.now().getEpochSecond();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), new ChatCompletionResponse(
                result.completionId(),
                "chat.completion",
                created,
                result.model(),
                List.of(new ChoiceResponse(
                        0,
                        new AssistantMessage("assistant", result.content()),
                        result.finishReason())),
                new UsageResponse(
                        result.promptTokens(),
                        result.completionTokens(),
                        result.totalTokens())));
    }

    private void writeStreamingCompletion(OpenAiCompatibleService.ChatCompletionResult result, HttpServletResponse response) throws IOException {
        long created = Instant.now().getEpochSecond();
        try (Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8)) {
            String contentChunk = objectMapper.writeValueAsString(new ChatCompletionChunkResponse(
                    result.completionId(),
                    "chat.completion.chunk",
                    created,
                    result.model(),
                    List.of(new StreamChoiceResponse(
                            0,
                            new DeltaMessage("assistant", result.content()),
                            null))));
            writer.write("data: " + contentChunk + "\n\n");

            String finishChunk = objectMapper.writeValueAsString(new ChatCompletionChunkResponse(
                    result.completionId(),
                    "chat.completion.chunk",
                    created,
                    result.model(),
                    List.of(new StreamChoiceResponse(
                            0,
                            new DeltaMessage(null, null),
                            result.finishReason()))));
            writer.write("data: " + finishChunk + "\n\n");
            writer.write("data: [DONE]\n\n");
            writer.flush();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ChatCompletionRequest(
            String model,
            @NotEmpty List<@Valid ChatMessageRequest> messages,
            Boolean stream) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ChatMessageRequest(
            @NotBlank String role,
            String content) {
    }

    public record ListModelsResponse(String object, List<ModelResponse> data) {
    }

    public record ModelResponse(
            String id,
            String object,
            long created,
            String owned_by,
            Integer context_window,
            String capabilities,
            String cost_information) {
    }

    public record ChatCompletionResponse(
            String id,
            String object,
            long created,
            String model,
            List<ChoiceResponse> choices,
            UsageResponse usage) {
    }

    public record ChoiceResponse(
            int index,
            AssistantMessage message,
            String finish_reason) {
    }

    public record AssistantMessage(String role, String content) {
    }

    public record UsageResponse(
            int prompt_tokens,
            int completion_tokens,
            int total_tokens) {
    }

    public record ChatCompletionChunkResponse(
            String id,
            String object,
            long created,
            String model,
            List<StreamChoiceResponse> choices) {
    }

    public record StreamChoiceResponse(
            int index,
            DeltaMessage delta,
            String finish_reason) {
    }

    public record DeltaMessage(String role, String content) {
    }
}
