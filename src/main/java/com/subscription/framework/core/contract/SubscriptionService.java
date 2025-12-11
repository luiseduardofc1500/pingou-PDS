package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Subscription;
import com.subscription.framework.core.domain.enums.SubscriptionStatus;

import java.util.List;
import java.util.Optional;


public interface SubscriptionService {
    
  
    Subscription createSubscription(Long customerId, Long planId);
    

    Subscription createTrialSubscription(Long customerId, Long planId, int trialDays);
    
   
    Subscription activate(Long subscriptionId);
    
  
    Subscription pause(Long subscriptionId);
    
    
    Subscription cancel(Long subscriptionId);
    
    
    Subscription renew(Long subscriptionId);
   
    Subscription changePlan(Long subscriptionId, Long newPlanId);
    
   
    Optional<Subscription> findById(Long id);
    
   
    Optional<Subscription> findActiveByCustomerId(Long customerId);

    List<Subscription> findByCustomerId(Long customerId);
  
    List<Subscription> findByStatus(SubscriptionStatus status);
 
    List<Subscription> findActiveByPlanId(Long planId);
   
    boolean hasActiveSubscription(Long customerId);
    
    
    void processExpiredSubscriptions();
    
   
    void processExpiredTrials();
}
