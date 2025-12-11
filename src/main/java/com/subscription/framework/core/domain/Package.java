package com.subscription.framework.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscription_packages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "package_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("BASE")
@Getter
@Setter
public class Package {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 2000)
    private String description;
    
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;
    
    @Column(nullable = false)
    private Integer month;
    
    @Column(nullable = false)
    private Integer year;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;
    
    @OneToMany(mappedBy = "pkg", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PackageItem> items = new ArrayList<>();
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(name = "total_value")
    private BigDecimal totalValue;
    
    @Column
    private String theme;
    
    public Package() {}
    
    public Package(String name, String description, LocalDate deliveryDate, 
                   Integer month, Integer year, Plan plan) {
        this.name = name;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.month = month;
        this.year = year;
        this.plan = plan;
    }
    
    public void addItem(PackageItem item) {
        items.add(item);
        item.setPkg(this);
        recalculateTotalValue();
    }
    
    public void removeItem(PackageItem item) {
        items.remove(item);
        item.setPkg(null);
        recalculateTotalValue();
    }
    
  
    public int getTotalItemCount() {
        return items.stream()
                .mapToInt(PackageItem::getQuantity)
                .sum();
    }
    
   
    public boolean canAddItems(int additionalQuantity) {
        return getTotalItemCount() + additionalQuantity <= plan.getMaxItemsPerDelivery();
    }
    
    public void recalculateTotalValue() {
        this.totalValue = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public String getFormattedPeriod() {
        return String.format("%02d/%d", month, year);
    }
}
