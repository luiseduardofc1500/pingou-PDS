package com.subscription.framework.api.exception;

/**
 * Exceção base para o framework de assinaturas.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public abstract class SubscriptionFrameworkException extends RuntimeException {
    
    private final String errorCode;
    
    protected SubscriptionFrameworkException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    protected SubscriptionFrameworkException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
