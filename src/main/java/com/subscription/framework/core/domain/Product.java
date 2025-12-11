package com.subscription.framework.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "products")
@Getter
@Setter
public abstract class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 2000)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(unique = true)
    private String sku;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column
    private String category;
    
    public Product() {}
    
    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
  
    public Product(String name, String description, BigDecimal price, String sku, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sku = sku;
        this.category = category;
    }
    
    public boolean isValidForPackage() {
        return active && price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }
}
