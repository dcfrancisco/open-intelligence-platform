package com.oip.domain.provider;

import com.oip.domain.shared.PersistableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "models")
public class Model extends PersistableEntity {

    @Column(name = "provider_id", nullable = false)
    private UUID providerId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 50)
    private String version;

    @Column(name = "context_window")
    private Integer contextWindow;

    @Column(length = 500)
    private String capabilities;

    @Column(name = "cost_information", length = 200)
    private String costInformation;

    @Column(name = "routing_priority", nullable = false)
    private int routingPriority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ModelStatus status;

    protected Model() {
    }

    public Model(
            UUID providerId,
            String name,
            String version,
            Integer contextWindow,
            String capabilities,
            String costInformation,
            int routingPriority,
            ModelStatus status) {
        this.providerId = providerId;
        this.name = name;
        this.version = version;
        this.contextWindow = contextWindow;
        this.capabilities = capabilities;
        this.costInformation = costInformation;
        this.routingPriority = routingPriority;
        this.status = status;
    }

    public UUID getProviderId() {
        return providerId;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Integer getContextWindow() {
        return contextWindow;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public String getCostInformation() {
        return costInformation;
    }

    public int getRoutingPriority() {
        return routingPriority;
    }

    public ModelStatus getStatus() {
        return status;
    }

    public boolean isEnabled() {
        return status == ModelStatus.ENABLED;
    }
}
