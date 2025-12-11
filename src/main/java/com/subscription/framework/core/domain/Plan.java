package com.subscription.framework.core.domain;

import com.subscription.framework.core.domain.enums.DeliveryFrequency;
import com.subscription.framework.core.domain.enums.PlanTier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "plan_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("BASE")
@Table(name = "plans")
public class Plan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome comercial do plano */
    @Column(nullable = false, unique = true)
    private String name;

    /** Descrição detalhada do plano e seus benefícios */
    @Column(nullable = false, length = 2000)
    private String description;

    /** Preço do plano por período */
    @Column(nullable = false)
    private BigDecimal price;
    
    /** Quantidade máxima de itens que podem ser enviados por período */
    @Column(name = "max_items_per_delivery", nullable = false)
    private Integer maxItemsPerDelivery;
    
    /** Frequência das entregas */
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_frequency", nullable = false)
    private DeliveryFrequency deliveryFrequency = DeliveryFrequency.MONTHLY;
    
    /** Nível (tier) do plano */
    @Enumerated(EnumType.STRING)
    @Column(name = "tier")
    private PlanTier tier = PlanTier.BASIC;
    
    /** Indica se o plano está ativo e disponível para assinatura */
    @Column(nullable = false)
    private Boolean active = true;
    
    /** Lista de pacotes associados a este plano */
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Package> packages = new ArrayList<>();
    
    /** Features/funcionalidades inclusas neste plano */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "plan_features",
        joinColumns = @JoinColumn(name = "plan_id"),
        inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private Set<Feature> features = new HashSet<>();
    
    /** Número de dias de trial (teste gratuito) */
    @Column(name = "trial_days")
    private Integer trialDays = 0;
    
    /** Desconto percentual para pagamento anual */
    @Column(name = "annual_discount_percent")
    private BigDecimal annualDiscountPercent;

    public Plan() {}

    public Plan(String name, String description, BigDecimal price, Integer maxItemsPerDelivery) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.maxItemsPerDelivery = maxItemsPerDelivery;
    }
    

    public Plan(String name, String description, BigDecimal price, 
                Integer maxItemsPerDelivery, DeliveryFrequency deliveryFrequency, PlanTier tier) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.maxItemsPerDelivery = maxItemsPerDelivery;
        this.deliveryFrequency = deliveryFrequency;
        this.tier = tier;
    }
    
   
    public void addPackage(Package pkg) {
        packages.add(pkg);
        pkg.setPlan(this);
    }
    
  
    public void removePackage(Package pkg) {
        packages.remove(pkg);
        pkg.setPlan(null);
    }
    
   
    public void addFeature(Feature feature) {
        features.add(feature);
    }
    
  
    public void removeFeature(Feature feature) {
        features.remove(feature);
    }
    
 
    public boolean hasFeature(String featureCode) {
        return features.stream().anyMatch(f -> f.getCode().equals(featureCode));
    }
    
    public BigDecimal calculateAnnualPrice() {
        int deliveriesPerYear = deliveryFrequency.getDeliveriesPerYear();
        BigDecimal annualPrice = price.multiply(BigDecimal.valueOf(deliveriesPerYear));
        
        if (annualDiscountPercent != null && annualDiscountPercent.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discount = annualPrice.multiply(annualDiscountPercent).divide(BigDecimal.valueOf(100));
            return annualPrice.subtract(discount);
        }
        
        return annualPrice;
    }
}
