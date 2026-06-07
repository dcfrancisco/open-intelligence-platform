package com.oip.api.provider;

import com.oip.domain.provider.ProviderService;
import com.oip.domain.provider.ProviderType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/providers")
@Tag(name = "Provider API")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register provider")
    public ProviderService.ProviderView register(@Valid @RequestBody RegisterProviderRequest request) {
        return providerService.register(new ProviderService.RegisterProviderCommand(
                request.workspaceId(),
                request.name(),
                request.providerType(),
                request.baseUrl(),
                request.modelName(),
                request.modelVersion(),
                request.contextWindow(),
                request.capabilities(),
                request.costInformation(),
                request.routingPriority(),
                request.enabled()));
    }

    @GetMapping
    @Operation(summary = "List providers")
    public List<ProviderService.ProviderView> list() {
        return providerService.list();
    }

    @PatchMapping("/{providerId}/enable")
    @Operation(summary = "Enable provider")
    public ProviderService.ProviderView enable(@PathVariable UUID providerId) {
        return providerService.enable(providerId);
    }

    @PatchMapping("/{providerId}/disable")
    @Operation(summary = "Disable provider")
    public ProviderService.ProviderView disable(@PathVariable UUID providerId) {
        return providerService.disable(providerId);
    }

    @GetMapping("/{providerId}/health")
    @Operation(summary = "Health check provider")
    public ProviderService.ProviderHealthView health(@PathVariable UUID providerId) {
        return providerService.healthCheck(providerId);
    }

    public record RegisterProviderRequest(
            UUID workspaceId,
            @NotBlank String name,
            @NotNull ProviderType providerType,
            @NotBlank String baseUrl,
            @NotBlank String modelName,
            String modelVersion,
            Integer contextWindow,
            String capabilities,
            String costInformation,
            int routingPriority,
            boolean enabled) {
    }
}
