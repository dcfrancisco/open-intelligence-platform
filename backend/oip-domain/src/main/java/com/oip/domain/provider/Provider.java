package com.oip.domain.provider;

import com.oip.domain.shared.PersistableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "providers")
public class Provider extends PersistableEntity {

    @Column(name = "workspace_id")
    private UUID workspaceId;

    @Column(nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = false, length = 50)
    private ProviderType providerType;

    @Column(name = "base_url", nullable = false, length = 500)
    private String baseUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ProviderStatus status;

    protected Provider() {
    }

    public Provider(UUID workspaceId, String name, ProviderType providerType, String baseUrl, ProviderStatus status) {
        this.workspaceId = workspaceId;
        this.name = name;
        this.providerType = providerType;
        this.baseUrl = baseUrl;
        this.status = status;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public String getName() {
        return name;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public ProviderStatus getStatus() {
        return status;
    }

    public boolean isEnabled() {
        return status == ProviderStatus.ENABLED;
    }

    public void enable() {
        this.status = ProviderStatus.ENABLED;
    }

    public void disable() {
        this.status = ProviderStatus.DISABLED;
    }

    public void markUnhealthy() {
        this.status = ProviderStatus.UNHEALTHY;
    }
}
