package com.oip.infrastructure.router;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.oip.domain.provider.Model;
import com.oip.domain.provider.ModelStatus;
import com.oip.domain.provider.Provider;
import com.oip.domain.provider.ProviderStatus;
import com.oip.domain.provider.ProviderType;
import com.oip.domain.router.ProviderRouter;
import com.oip.infrastructure.provider.ModelJpaRepository;
import com.oip.infrastructure.provider.ProviderJpaRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DefaultProviderRouterTest {

    private final ProviderJpaRepository providerJpaRepository = mock(ProviderJpaRepository.class);
    private final ModelJpaRepository modelJpaRepository = mock(ModelJpaRepository.class);
    private final DefaultProviderRouter router = new DefaultProviderRouter(providerJpaRepository, modelJpaRepository);

    @Test
    void routesToFirstEnabledProviderAndModel() {
        Provider disabledProvider = new Provider(null, "Disabled", ProviderType.OLLAMA, "http://disabled", ProviderStatus.DISABLED);
        Provider enabledProvider = new Provider(null, "Ollama Local", ProviderType.OLLAMA, "http://ollama:11434", ProviderStatus.ENABLED);
        Model disabledModel = new Model(UUID.randomUUID(), "llama3.2:1b", null, 8192, "chat", "local", 50, ModelStatus.DISABLED);
        Model enabledModel = new Model(UUID.randomUUID(), "llama3.2:3b", null, 8192, "chat", "local", 100, ModelStatus.ENABLED);

        when(providerJpaRepository.findByStatusOrderByCreatedAtAsc(ProviderStatus.ENABLED))
                .thenReturn(List.of(enabledProvider));
        when(modelJpaRepository.findByProviderIdOrderByRoutingPriorityAscCreatedAtAsc(enabledProvider.getId()))
                .thenReturn(List.of(disabledModel, enabledModel));

        ProviderRouter.ProviderSelection selection = router.route(new ProviderRouter.AskRoutingRequest(UUID.randomUUID(), "How do we deploy OIP?"));

        assertThat(selection.providerName()).isEqualTo("Ollama Local");
        assertThat(selection.baseUrl()).isEqualTo("http://ollama:11434");
        assertThat(selection.modelName()).isEqualTo("llama3.2:3b");
    }

    @Test
    void failsWhenNoProviderIsEnabled() {
        when(providerJpaRepository.findByStatusOrderByCreatedAtAsc(ProviderStatus.ENABLED))
                .thenReturn(List.of());

        assertThatThrownBy(() -> router.route(new ProviderRouter.AskRoutingRequest(UUID.randomUUID(), "hello")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("No enabled provider");
    }
}
