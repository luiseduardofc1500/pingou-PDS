package com.subscription.framework.core.contract;


public interface NotificationService {
    
 
    void notifySubscriptionCreated(Long customerId, Long subscriptionId);
    
   
    void notifySubscriptionActivated(Long customerId, Long subscriptionId);
    
    
    void notifySubscriptionCancelled(Long customerId, Long subscriptionId);
    
  
    void notifyExpirationWarning(Long customerId, Long subscriptionId, int daysUntilExpiration);
    

    void notifySubscriptionRenewed(Long customerId, Long subscriptionId);
   
    void notifyPackageShipped(Long customerId, Long packageId, String trackingCode);
    
  
    void notifyPackageDelivered(Long customerId, Long packageId);
    

    void notifyPaymentFailed(Long customerId, Long subscriptionId, String reason);
    
  
    void notifyTrialEnding(Long customerId, Long subscriptionId, int daysRemaining);
    
  
    void sendNotification(Long customerId, String subject, String message, NotificationType type);
    
    
    enum NotificationType {
        EMAIL,
        SMS,
        PUSH,
        IN_APP
    }
}
