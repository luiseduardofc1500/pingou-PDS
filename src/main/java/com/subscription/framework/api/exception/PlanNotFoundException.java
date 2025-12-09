package com.subscription.framework.api.exception;

/**
 * Exceção lançada quando um plano não é encontrado.
 */
public class PlanNotFoundException extends SubscriptionFrameworkException {
    
    public PlanNotFoundException(Long planId) {
        super("Plan not found: " + planId, "PLAN_NOT_FOUND");
    }
    
    public PlanNotFoundException(String planName) {
        super("Plan not found: " + planName, "PLAN_NOT_FOUND");
    }
}
