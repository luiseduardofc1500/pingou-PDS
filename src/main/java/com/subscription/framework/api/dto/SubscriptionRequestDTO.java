package com.subscription.framework.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisição de criação/atualização de assinatura.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Getter
@Setter
public class SubscriptionRequestDTO {
    
    private Long customerId;
    private Long planId;
    private Boolean startTrial = false;
    private Integer trialDays;
}
