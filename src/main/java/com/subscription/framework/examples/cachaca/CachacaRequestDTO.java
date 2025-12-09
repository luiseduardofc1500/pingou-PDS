package com.subscription.framework.examples.cachaca;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO de requisição para cachaça.
 */
@Getter
@Setter
public class CachacaRequestDTO {
    
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String sku;
    
    // Campos específicos de cachaça
    private String region;
    private BigDecimal alcoholContent;
    private Integer volume;
    private CachacaType cachacaType;
    private AgingType agingType;
    private Integer agingMonths;
    private String distillery;
    private Integer productionYear;
}
