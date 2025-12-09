package com.subscription.framework.api.exception;

/**
 * Exceção lançada quando uma assinatura não é encontrada.
 */
public class SubscriptionNotFoundException extends SubscriptionFrameworkException {
    
    public SubscriptionNotFoundException(Long subscriptionId) {
        super("Subscription not found: " + subscriptionId, "SUBSCRIPTION_NOT_FOUND");
    }
    
    public SubscriptionNotFoundException(String message) {
        super(message, "SUBSCRIPTION_NOT_FOUND");
    }
}
