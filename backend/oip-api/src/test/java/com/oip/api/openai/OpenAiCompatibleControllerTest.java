package com.oip.api.openai;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oip.api.config.ApiExceptionHandler;
import com.oip.api.config.RequestIdFilter;
import com.oip.domain.provider.OpenAiCompatibleService;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class OpenAiCompatibleControllerTest {

    @Test
    void listsEnabledModelsInOpenAiSchema() throws Exception {
        MockMvc mockMvc = mockMvc(new StubOpenAiCompatibleService(
                List.of(new OpenAiCompatibleService.AvailableModel(
                        "qwen2.5-coder:7b",
                        "Ollama Local",
                        "OLLAMA",
                        32768,
                        "chat,coding",
                        "local")),
                null));

        mockMvc.perform(get("/v1/models"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.object").value("list"))
                .andExpect(jsonPath("$.data[0].id").value("qwen2.5-coder:7b"))
                .andExpect(jsonPath("$.data[0].owned_by").value("Ollama Local"));
    }

    @Test
    void returnsNonStreamingChatCompletion() throws Exception {
        MockMvc mockMvc = mockMvc(new StubOpenAiCompatibleService(
                List.of(),
                new OpenAiCompatibleService.ChatCompletionResult(
                        "chatcmpl-123",
                        "qwen2.5-coder:7b",
                        "Use the workspace memory as context before answering.",
                        "stop",
                        24,
                        12,
                        36)));

        mockMvc.perform(post("/v1/chat/completions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "model": "qwen2.5-coder:7b",
                                  "messages": [
                                    {"role": "system", "content": "You are OIP."},
                                    {"role": "user", "content": "Explain memory-first architecture."}
                                  ]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("chatcmpl-123"))
                .andExpect(jsonPath("$.object").value("chat.completion"))
                .andExpect(jsonPath("$.choices[0].message.role").value("assistant"))
                .andExpect(jsonPath("$.choices[0].message.content").value("Use the workspace memory as context before answering."))
                .andExpect(jsonPath("$.usage.total_tokens").value(36));
    }

    @Test
    void returnsStreamingChatCompletionUsingServerSentEvents() throws Exception {
        MockMvc mockMvc = mockMvc(new StubOpenAiCompatibleService(
                List.of(),
                new OpenAiCompatibleService.ChatCompletionResult(
                        "chatcmpl-456",
                        "qwen2.5-coder:7b",
                        "Streaming through the OpenAI-compatible surface.",
                        "stop",
                        20,
                        10,
                        30)));

        mockMvc.perform(post("/v1/chat/completions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "model": "qwen2.5-coder:7b",
                                  "stream": true,
                                  "messages": [
                                    {"role": "user", "content": "Stream the answer."}
                                  ]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM))
                .andExpect(content().string(Matchers.containsString("data: {")))
                .andExpect(content().string(Matchers.containsString("\"object\":\"chat.completion.chunk\"")))
                .andExpect(content().string(Matchers.containsString("data: [DONE]")));
    }

    private MockMvc mockMvc(OpenAiCompatibleService service) {
        return MockMvcBuilders.standaloneSetup(new OpenAiCompatibleController(service, new ObjectMapper()))
                .setControllerAdvice(new ApiExceptionHandler())
                .addFilters(new RequestIdFilter())
                .build();
    }

    private record StubOpenAiCompatibleService(
            List<OpenAiCompatibleService.AvailableModel> models,
            OpenAiCompatibleService.ChatCompletionResult completionResult) implements OpenAiCompatibleService {

        @Override
        public List<AvailableModel> listModels() {
            return models;
        }

        @Override
        public ChatCompletionResult chatCompletion(ChatCompletionCommand command) {
            return completionResult;
        }
    }
}
