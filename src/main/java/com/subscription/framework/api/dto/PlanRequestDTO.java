package com.subscription.framework.api.dto;

import com.subscription.framework.core.domain.enums.DeliveryFrequency;
import com.subscription.framework.core.domain.enums.PlanTier;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para requisição de criação/atualização de plano.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Getter
@Setter
public class PlanRequestDTO {
    
    private String name;
    private String description;
    private BigDecimal price;
    private Integer maxItemsPerDelivery;
    private DeliveryFrequency deliveryFrequency = DeliveryFrequency.MONTHLY;
    private PlanTier tier = PlanTier.BASIC;
    private Integer trialDays = 0;
    private BigDecimal annualDiscountPercent;
}
