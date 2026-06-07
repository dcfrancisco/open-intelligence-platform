package com.oip.api.health;

import com.oip.domain.provider.ProviderHealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health API")
public class HealthController {

    private final ProviderHealthService providerHealthService;

    public HealthController(ProviderHealthService providerHealthService) {
        this.providerHealthService = providerHealthService;
    }

    @GetMapping
    @Operation(summary = "Application health")
    public HealthResponse health() {
        return new HealthResponse("UP", OffsetDateTime.now(), providerHealthService.allProviderHealth());
    }

    public record HealthResponse(
            String status,
            OffsetDateTime timestamp,
            List<com.oip.domain.provider.ProviderService.ProviderHealthView> providers) {
    }
}
