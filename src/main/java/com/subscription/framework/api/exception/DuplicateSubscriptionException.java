package com.subscription.framework.api.exception;

/**
 * Exceção lançada quando o cliente já possui uma assinatura ativa.
 */
public class DuplicateSubscriptionException extends SubscriptionFrameworkException {
    
    public DuplicateSubscriptionException(String message) {
        super(message, "DUPLICATE_SUBSCRIPTION");
    }
}
