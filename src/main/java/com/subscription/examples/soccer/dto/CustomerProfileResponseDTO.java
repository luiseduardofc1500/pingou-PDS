package com.subscription.examples.soccer.dto;

import com.subscription.examples.soccer.domain.SoccerCustomerProfile;

public class CustomerProfileResponseDTO {

    private Long customerId;
    private Double heightCm;
    private Double weightKg;
    private Double chestCm;
    private Double waistCm;
    private String recommendedSize;

    public static CustomerProfileResponseDTO fromEntity(SoccerCustomerProfile profile) {
        CustomerProfileResponseDTO dto = new CustomerProfileResponseDTO();
        dto.customerId = profile.getCustomerId();
        dto.heightCm = profile.getHeightCm();
        dto.weightKg = profile.getWeightKg();
        dto.chestCm = profile.getChestCm();
        dto.waistCm = profile.getWaistCm();
        dto.recommendedSize = profile.getRecommendedSize();
        return dto;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public Double getChestCm() {
        return chestCm;
    }

    public Double getWaistCm() {
        return waistCm;
    }

    public String getRecommendedSize() {
        return recommendedSize;
    }
}
