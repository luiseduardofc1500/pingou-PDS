package com.subscription.framework.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "features")
@Getter
@Setter
public class Feature {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column
    private String icon;
    
    public Feature() {}
    
    public Feature(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
    
  
    public static final String FREE_SHIPPING = "FREE_SHIPPING";
    public static final String EARLY_ACCESS = "EARLY_ACCESS";
    public static final String EXCLUSIVE_GIFTS = "EXCLUSIVE_GIFTS";
    public static final String PRIORITY_SUPPORT = "PRIORITY_SUPPORT";
    public static final String ADDITIONAL_DISCOUNT = "ADDITIONAL_DISCOUNT";
}
