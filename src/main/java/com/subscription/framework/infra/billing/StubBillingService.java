package com.subscription.framework.infra.billing;

import com.subscription.framework.core.contract.BillingService;
import com.subscription.framework.core.domain.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;


public class StubBillingService implements BillingService {
    
    private static final Logger logger = LoggerFactory.getLogger(StubBillingService.class);
    
    @Override
    public String processPayment(Subscription subscription) {
        logger.info("Processing payment for subscription {} - Plan: {} - Amount: {}", 
                subscription.getId(), 
                subscription.getPlan().getName(),
                subscription.getPlan().getPrice());
        
        // Simula o processamento do pagamento
        String transactionId = "TXN-" + System.currentTimeMillis();
        logger.info("Payment processed successfully. Transaction ID: {}", transactionId);
        
        return transactionId;
    }
    
    @Override
    public String processPayment(Long customerId, BigDecimal amount, String description) {
        logger.info("Processing payment for customer {} - Amount: {} - Description: {}", 
                customerId, amount, description);
        
        String transactionId = "TXN-" + System.currentTimeMillis();
        logger.info("Payment processed successfully. Transaction ID: {}", transactionId);
        
        return transactionId;
    }
    
    @Override
    public boolean cancelRecurringPayment(String externalPaymentId) {
        logger.info("Cancelling recurring payment: {}", externalPaymentId);
        return true;
    }
    
    @Override
    public String refund(String externalPaymentId, BigDecimal amount) {
        logger.info("Processing refund for transaction {} - Amount: {}", 
                externalPaymentId, amount != null ? amount : "FULL");
        
        String refundId = "REF-" + System.currentTimeMillis();
        logger.info("Refund processed successfully. Refund ID: {}", refundId);
        
        return refundId;
    }
    
    @Override
    public PaymentStatus getPaymentStatus(String externalPaymentId) {
        logger.info("Getting payment status for: {}", externalPaymentId);
        return PaymentStatus.COMPLETED;
    }
    
    @Override
    public String createCheckoutSession(Long customerId, Long planId, 
                                         String successUrl, String cancelUrl) {
        logger.info("Creating checkout session for customer {} - Plan: {}", customerId, planId);
        
        // Retorna uma URL fake para o checkout
        String sessionUrl = "https://checkout.example.com/session/" + System.currentTimeMillis();
        logger.info("Checkout session created: {}", sessionUrl);
        
        return sessionUrl;
    }
}
