package com.subscription.framework.core.domain;

import com.subscription.framework.core.domain.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "subscription_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("BASE")
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "customer_email")
    private String customerEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    @Column(name = "cancelled_date")
    private LocalDate cancelledDate;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "external_payment_id")
    private String externalPaymentId;
    
    @Column(name = "is_trial")
    private Boolean isTrial = false;
    
    @Column(name = "trial_end_date")
    private LocalDate trialEndDate;

    public Subscription() {
        this.createdAt = LocalDateTime.now();
    }

   
    public Subscription(Long customerId, Plan plan, SubscriptionStatus status, 
                        LocalDate startDate, LocalDate expirationDate) {
        this.customerId = customerId;
        this.plan = plan;
        this.status = status;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.createdAt = LocalDateTime.now();
    }
    
   
    public void activate() {
        this.status = SubscriptionStatus.ACTIVE;
        this.isTrial = false;
        this.updatedAt = LocalDateTime.now();
    }
    
  
    public void pause() {
        if (this.status.canPause()) {
            this.status = SubscriptionStatus.PAUSED;
            this.updatedAt = LocalDateTime.now();
        }
    }
    
  
    public void cancel() {
        if (this.status.canCancel()) {
            this.status = SubscriptionStatus.CANCELLED;
            this.cancelledDate = LocalDate.now();
            this.updatedAt = LocalDateTime.now();
        }
    }
    
  
    public void renew() {
        if (this.status.canRenew()) {
            this.status = SubscriptionStatus.ACTIVE;
            this.startDate = LocalDate.now();
            this.expirationDate = calculateNextExpirationDate();
            this.updatedAt = LocalDateTime.now();
        }
    }
    
   
    private LocalDate calculateNextExpirationDate() {
        return LocalDate.now().plusDays(plan.getDeliveryFrequency().getDaysInterval());
    }
    
    public boolean isExpired() {
        return expirationDate != null && LocalDate.now().isAfter(expirationDate);
    }
    
   
    public boolean isInTrial() {
        return isTrial && trialEndDate != null && !LocalDate.now().isAfter(trialEndDate);
    }
    
 
    public void startTrial(int days) {
        this.isTrial = true;
        this.trialEndDate = LocalDate.now().plusDays(days);
        this.status = SubscriptionStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
