package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Subscription;

import java.math.BigDecimal;


public interface BillingService {
    
    String processPayment(Subscription subscription);

    String processPayment(Long customerId, BigDecimal amount, String description);

    boolean cancelRecurringPayment(String externalPaymentId);
    
    String refund(String externalPaymentId, BigDecimal amount);

    PaymentStatus getPaymentStatus(String externalPaymentId);
    
    String createCheckoutSession(Long customerId, Long planId, String successUrl, String cancelUrl);
    
    enum PaymentStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        REFUNDED,
        CANCELLED
    }
}
