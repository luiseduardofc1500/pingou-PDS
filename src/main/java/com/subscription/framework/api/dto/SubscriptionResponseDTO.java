package com.subscription.framework.api.dto;

import com.subscription.framework.core.domain.Subscription;
import com.subscription.framework.core.domain.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para resposta de assinatura.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Getter
@Setter
public class SubscriptionResponseDTO {
    
    private Long id;
    private Long customerId;
    private String customerEmail;
    private Long planId;
    private String planName;
    private SubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private LocalDate cancelledDate;
    private Boolean isTrial;
    private LocalDate trialEndDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Converte uma entidade Subscription para DTO.
     */
    public static SubscriptionResponseDTO fromEntity(Subscription subscription) {
        SubscriptionResponseDTO dto = new SubscriptionResponseDTO();
        dto.setId(subscription.getId());
        dto.setCustomerId(subscription.getCustomerId());
        dto.setCustomerEmail(subscription.getCustomerEmail());
        dto.setPlanId(subscription.getPlan().getId());
        dto.setPlanName(subscription.getPlan().getName());
        dto.setStatus(subscription.getStatus());
        dto.setStartDate(subscription.getStartDate());
        dto.setExpirationDate(subscription.getExpirationDate());
        dto.setCancelledDate(subscription.getCancelledDate());
        dto.setIsTrial(subscription.getIsTrial());
        dto.setTrialEndDate(subscription.getTrialEndDate());
        dto.setCreatedAt(subscription.getCreatedAt());
        dto.setUpdatedAt(subscription.getUpdatedAt());
        return dto;
    }
}
