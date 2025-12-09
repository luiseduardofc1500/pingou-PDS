package com.subscription.framework.core.domain.enums;

/**
 * Enum genérico que representa os possíveis status de uma assinatura.
 * 
 * Este enum é parte do framework de assinaturas e pode ser utilizado
 * por qualquer implementação concreta, independente do tipo de produto.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public enum SubscriptionStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    CANCELLED("Cancelled"),
    EXPIRED("Expired"),
    PAUSED("Paused"),
    PENDING("Pending");

    private final String description;

    SubscriptionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean canRenew() {
        return this == EXPIRED || this == INACTIVE || this == PAUSED;
    }

    public boolean canUse() {
        return this == ACTIVE;
    }

    public boolean canCancel() {
        return this == ACTIVE || this == PAUSED;
    }

    public boolean canPause() {
        return this == ACTIVE;
    }
}
