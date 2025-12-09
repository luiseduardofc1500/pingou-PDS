package com.subscription.framework.api.exception;

/**
 * Exceção lançada quando uma feature não é encontrada.
 */
public class FeatureNotFoundException extends SubscriptionFrameworkException {
    
    public FeatureNotFoundException(Long featureId) {
        super("Feature not found: " + featureId, "FEATURE_NOT_FOUND");
    }
    
    public FeatureNotFoundException(String featureCode) {
        super("Feature not found: " + featureCode, "FEATURE_NOT_FOUND");
    }
}
