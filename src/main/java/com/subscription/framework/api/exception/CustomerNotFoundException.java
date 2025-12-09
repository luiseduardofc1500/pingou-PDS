package com.subscription.framework.api.exception;

/**
 * Exceção lançada quando o cliente não é encontrado.
 */
public class CustomerNotFoundException extends SubscriptionFrameworkException {
    
    public CustomerNotFoundException(Long customerId) {
        super("Customer not found: " + customerId, "CUSTOMER_NOT_FOUND");
    }
    
    public CustomerNotFoundException(String identifier) {
        super("Customer not found: " + identifier, "CUSTOMER_NOT_FOUND");
    }
}
