package com.subscription.framework.hq.dto;

import com.subscription.framework.core.domain.enums.DeliveryFrequency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanoHQResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer maxItemsPerDelivery;
    private DeliveryFrequency deliveryFrequency;
    private Integer percentualClassicas;
    private Integer percentualModernas;
    private Boolean incluiEdicoesColecionador;
    private Double multiplicadorPontos;
    private String filosofiaCuradoria;
    private Boolean planoColecionador;
    private Boolean active;
}
