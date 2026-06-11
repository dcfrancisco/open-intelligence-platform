package com.oip.infrastructure.provider;

import com.oip.infrastructure.provider.OllamaModels.OllamaChatRequest;
import com.oip.infrastructure.provider.OllamaModels.OllamaChatResponse;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class OllamaClient {

    private final RestClient.Builder restClientBuilder;

    public OllamaClient(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    public boolean isHealthy(String baseUrl) {
        try {
            restClientBuilder.baseUrl(baseUrl)
                    .build()
                    .get()
                    .uri("/api/tags")
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (RestClientException ex) {
            return false;
        }
    }

    public String chat(String baseUrl, String model, String prompt) {
        return chat(baseUrl, model, java.util.List.of(new OllamaModels.OllamaMessage("user", prompt)));
    }

    public String chat(String baseUrl, String model, List<OllamaModels.OllamaMessage> messages) {
        OllamaChatResponse response = restClientBuilder.baseUrl(baseUrl)
                .build()
                .post()
                .uri("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new OllamaChatRequest(model, messages, false))
                .retrieve()
                .body(OllamaChatResponse.class);
        if (response == null || response.message() == null || response.message().content() == null) {
            throw new IllegalStateException("Ollama returned an empty response");
        }
        return response.message().content();
    }
}
