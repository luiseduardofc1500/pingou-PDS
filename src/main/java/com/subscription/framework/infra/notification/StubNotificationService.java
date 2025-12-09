package com.subscription.framework.infra.notification;

import com.subscription.framework.core.contract.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementação stub do serviço de notificações.
 * 
 * <p><b>Hotspot de Extensão:</b> Esta é uma implementação de exemplo que
 * apenas loga as notificações. Para produção, crie uma implementação que
 * envie emails, SMS, push notifications, etc.</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public class StubNotificationService implements NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(StubNotificationService.class);
    
    @Override
    public void notifySubscriptionCreated(Long customerId, Long subscriptionId) {
        logger.info("[NOTIFICATION] Subscription created - Customer: {} - Subscription: {}", 
                customerId, subscriptionId);
    }
    
    @Override
    public void notifySubscriptionActivated(Long customerId, Long subscriptionId) {
        logger.info("[NOTIFICATION] Subscription activated - Customer: {} - Subscription: {}", 
                customerId, subscriptionId);
    }
    
    @Override
    public void notifySubscriptionCancelled(Long customerId, Long subscriptionId) {
        logger.info("[NOTIFICATION] Subscription cancelled - Customer: {} - Subscription: {}", 
                customerId, subscriptionId);
    }
    
    @Override
    public void notifyExpirationWarning(Long customerId, Long subscriptionId, int daysUntilExpiration) {
        logger.info("[NOTIFICATION] Expiration warning - Customer: {} - Subscription: {} - Days: {}", 
                customerId, subscriptionId, daysUntilExpiration);
    }
    
    @Override
    public void notifySubscriptionRenewed(Long customerId, Long subscriptionId) {
        logger.info("[NOTIFICATION] Subscription renewed - Customer: {} - Subscription: {}", 
                customerId, subscriptionId);
    }
    
    @Override
    public void notifyPackageShipped(Long customerId, Long packageId, String trackingCode) {
        logger.info("[NOTIFICATION] Package shipped - Customer: {} - Package: {} - Tracking: {}", 
                customerId, packageId, trackingCode);
    }
    
    @Override
    public void notifyPackageDelivered(Long customerId, Long packageId) {
        logger.info("[NOTIFICATION] Package delivered - Customer: {} - Package: {}", 
                customerId, packageId);
    }
    
    @Override
    public void notifyPaymentFailed(Long customerId, Long subscriptionId, String reason) {
        logger.info("[NOTIFICATION] Payment failed - Customer: {} - Subscription: {} - Reason: {}", 
                customerId, subscriptionId, reason);
    }
    
    @Override
    public void notifyTrialEnding(Long customerId, Long subscriptionId, int daysRemaining) {
        logger.info("[NOTIFICATION] Trial ending - Customer: {} - Subscription: {} - Days remaining: {}", 
                customerId, subscriptionId, daysRemaining);
    }
    
    @Override
    public void sendNotification(Long customerId, String subject, String message, NotificationType type) {
        logger.info("[NOTIFICATION] {} - Customer: {} - Subject: {} - Message: {}", 
                type, customerId, subject, message);
    }
}
