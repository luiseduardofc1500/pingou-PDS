package com.subscription.framework.core.domain;

import com.subscription.framework.core.domain.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_records")
@Getter
@Setter
public class DeliveryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private Package pkg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;
    
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status = DeliveryStatus.PENDING;
    
    @Column(name = "tracking_code")
    private String trackingCode;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(name = "delivery_address", length = 2000)
    private String deliveryAddress;

    public DeliveryRecord() {}
    
    
    public DeliveryRecord(Long customerId, Package pkg, Product product, Integer quantity) {
        this.customerId = customerId;
        this.pkg = pkg;
        this.product = product;
        this.quantity = quantity;
        this.sentAt = LocalDateTime.now();
        this.status = DeliveryStatus.PENDING;
    }
    
  
    public void markAsShipped(String trackingCode) {
        this.status = DeliveryStatus.SHIPPED;
        this.trackingCode = trackingCode;
    }
    
    public void markAsInTransit() {
        this.status = DeliveryStatus.IN_TRANSIT;
    }
    
  
    public void markAsDelivered() {
        this.status = DeliveryStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
    }
    

    public void markAsFailed(String reason) {
        this.status = DeliveryStatus.FAILED;
        this.notes = reason;
    }
}
