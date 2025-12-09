package com.subscription.framework.api.exception;

/**
 * Exceção lançada quando já existe um plano com o mesmo nome.
 */
public class DuplicatePlanNameException extends SubscriptionFrameworkException {
    
    public DuplicatePlanNameException(String planName) {
        super("A plan with this name already exists: " + planName, "DUPLICATE_PLAN_NAME");
    }
}
