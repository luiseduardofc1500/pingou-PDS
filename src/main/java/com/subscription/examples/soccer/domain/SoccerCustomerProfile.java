package com.subscription.examples.soccer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "soccer_customer_profiles")
public class SoccerCustomerProfile {

    @Id
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "height_cm", nullable = false)
    private Double heightCm;

    @Column(name = "weight_kg", nullable = false)
    private Double weightKg;

    @Column(name = "chest_cm")
    private Double chestCm;

    @Column(name = "waist_cm")
    private Double waistCm;

    @Column(name = "recommended_size", nullable = false)
    private String recommendedSize; // JerseySize

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Double heightCm) {
        this.heightCm = heightCm;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public Double getChestCm() {
        return chestCm;
    }

    public void setChestCm(Double chestCm) {
        this.chestCm = chestCm;
    }

    public Double getWaistCm() {
        return waistCm;
    }

    public void setWaistCm(Double waistCm) {
        this.waistCm = waistCm;
    }

    public String getRecommendedSize() {
        return recommendedSize;
    }

    public void setRecommendedSize(String recommendedSize) {
        this.recommendedSize = recommendedSize;
    }
}
