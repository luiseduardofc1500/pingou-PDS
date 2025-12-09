package com.subscription.framework.api.dto;

import com.subscription.framework.core.domain.Plan;
import com.subscription.framework.core.domain.enums.DeliveryFrequency;
import com.subscription.framework.core.domain.enums.PlanTier;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para resposta de plano.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Getter
@Setter
public class PlanResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer maxItemsPerDelivery;
    private DeliveryFrequency deliveryFrequency;
    private PlanTier tier;
    private Boolean active;
    private Integer trialDays;
    private BigDecimal annualDiscountPercent;
    private BigDecimal annualPrice;
    private List<String> featureCodes;
    
    /**
     * Converte uma entidade Plan para DTO.
     */
    public static PlanResponseDTO fromEntity(Plan plan) {
        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setId(plan.getId());
        dto.setName(plan.getName());
        dto.setDescription(plan.getDescription());
        dto.setPrice(plan.getPrice());
        dto.setMaxItemsPerDelivery(plan.getMaxItemsPerDelivery());
        dto.setDeliveryFrequency(plan.getDeliveryFrequency());
        dto.setTier(plan.getTier());
        dto.setActive(plan.getActive());
        dto.setTrialDays(plan.getTrialDays());
        dto.setAnnualDiscountPercent(plan.getAnnualDiscountPercent());
        dto.setAnnualPrice(plan.calculateAnnualPrice());
        dto.setFeatureCodes(plan.getFeatures().stream()
                .map(f -> f.getCode())
                .toList());
        return dto;
    }
}
