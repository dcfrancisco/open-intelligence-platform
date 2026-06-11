package com.oip.infrastructure.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.oip.domain.provider.ModelStatus;
import com.oip.domain.provider.OpenAiCompatibleService;
import com.oip.domain.provider.ProviderService;
import com.oip.domain.provider.ProviderStatus;
import com.oip.domain.provider.ProviderType;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class OpenAiCompatibleServiceImplTest {

    @Test
    void listsOnlyEnabledModels() {
        OpenAiCompatibleServiceImpl service = new OpenAiCompatibleServiceImpl(
                new StubProviderService(List.of(
                        providerView("Ollama Local", ProviderStatus.ENABLED, "qwen2.5-coder:7b", ModelStatus.ENABLED, 10),
                        providerView("Disabled Provider", ProviderStatus.DISABLED, "llama3.2:3b", ModelStatus.ENABLED, 20))),
                new StubOllamaClient("unused"));

        List<OpenAiCompatibleService.AvailableModel> models = service.listModels();

        assertThat(models).hasSize(1);
        assertThat(models.getFirst().id()).isEqualTo("qwen2.5-coder:7b");
    }

    @Test
    void routesRequestedModelToOllamaCompatibleBackend() {
        OpenAiCompatibleServiceImpl service = new OpenAiCompatibleServiceImpl(
                new StubProviderService(List.of(
                        providerView("Ollama Local", ProviderStatus.ENABLED, "llama3.2:3b", ModelStatus.ENABLED, 20),
                        providerView("Ollama Local", ProviderStatus.ENABLED, "qwen2.5-coder:7b", ModelStatus.ENABLED, 10))),
                new StubOllamaClient("Routing prefers enabled configured models."));

        OpenAiCompatibleService.ChatCompletionResult result = service.chatCompletion(
                new OpenAiCompatibleService.ChatCompletionCommand(
                        "qwen2.5-coder:7b",
                        List.of(
                                new OpenAiCompatibleService.ChatMessage("system", "You are OIP."),
                                new OpenAiCompatibleService.ChatMessage("user", "Explain routing."))));

        assertThat(result.model()).isEqualTo("qwen2.5-coder:7b");
        assertThat(result.content()).contains("Routing");
        assertThat(result.totalTokens()).isGreaterThan(0);
    }

    @Test
    void rejectsUnknownRequestedModel() {
        OpenAiCompatibleServiceImpl service = new OpenAiCompatibleServiceImpl(
                new StubProviderService(List.of(
                        providerView("Ollama Local", ProviderStatus.ENABLED, "qwen2.5-coder:7b", ModelStatus.ENABLED, 10))),
                new StubOllamaClient("unused"));

        assertThatThrownBy(() -> service.chatCompletion(new OpenAiCompatibleService.ChatCompletionCommand(
                        "unknown-model",
                        List.of(new OpenAiCompatibleService.ChatMessage("user", "Hello")))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Requested model is not available");
    }

    private static ProviderService.ProviderView providerView(
            String providerName,
            ProviderStatus providerStatus,
            String modelName,
            ModelStatus modelStatus,
            int routingPriority) {
        return new ProviderService.ProviderView(
                UUID.randomUUID(),
                null,
                providerName,
                ProviderType.OLLAMA,
                "http://ollama:11434",
                providerStatus,
                List.of(new ProviderService.ModelView(
                        UUID.randomUUID(),
                        modelName,
                        null,
                        32768,
                        "chat,coding",
                        "local",
                        routingPriority,
                        modelStatus)));
    }

    private record StubProviderService(List<ProviderService.ProviderView> providers) implements ProviderService {

        @Override
        public ProviderView register(RegisterProviderCommand command) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<ProviderView> list() {
            return providers;
        }

        @Override
        public ProviderView enable(UUID providerId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ProviderView disable(UUID providerId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ProviderHealthView healthCheck(UUID providerId) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class StubOllamaClient extends OllamaClient {

        private final String response;

        private StubOllamaClient(String response) {
            super(RestClient.builder());
            this.response = response;
        }

        @Override
        public String chat(String baseUrl, String model, List<OllamaModels.OllamaMessage> messages) {
            return response;
        }
    }
}
