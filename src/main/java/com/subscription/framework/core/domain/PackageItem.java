package com.subscription.framework.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "package_items")
@Getter
@Setter
public class PackageItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private Package pkg;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity = 1;
    
    @Column(length = 500)
    private String notes;
    
    @Column(name = "unit_price_at_time")
    private BigDecimal unitPriceAtTime;
    
    public PackageItem() {}
    
    public PackageItem(Package pkg, Product product, Integer quantity) {
        this.pkg = pkg;
        this.product = product;
        this.quantity = quantity;
        this.unitPriceAtTime = product.getPrice();
    }
    
    public BigDecimal getSubtotal() {
        BigDecimal price = unitPriceAtTime != null ? unitPriceAtTime : product.getPrice();
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    
    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = newQuantity;
    }
}
