package com.subscription.framework.core.domain.enums;


public enum DeliveryStatus {
    PENDING("Pending"),
    PROCESSING("Processing"),
    SHIPPED("Shipped"),
    IN_TRANSIT("In Transit"),
    DELIVERED("Delivered"),
    FAILED("Failed"),
    RETURNED("Returned"),
    CANCELLED("Cancelled");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return this == DELIVERED;
    }

    public boolean isFailed() {
        return this == FAILED || this == RETURNED;
    }

    public boolean isInProgress() {
        return this == PENDING || this == PROCESSING || this == SHIPPED || this == IN_TRANSIT;
    }

    public boolean canRetry() {
        return this == FAILED;
    }
}
