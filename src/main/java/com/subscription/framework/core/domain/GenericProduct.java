package com.subscription.framework.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@DiscriminatorValue("GENERIC")
@Getter
@Setter
public class GenericProduct extends Product {
    
    @Column(name = "generic_product_type")
    private String productType;
    
    @Column(name = "generic_metadata", length = 4000)
    private String metadata;
    
    public GenericProduct() {
        super();
    }
    
    public GenericProduct(String name, String description, java.math.BigDecimal price) {
        super();
        setName(name);
        setDescription(description);
        setPrice(price);
    }
}
