package com.subscription.framework.examples.cachaca;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO de resposta para cachaça.
 */
@Getter
@Setter
public class CachacaResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String sku;
    private String category;
    private Boolean active;
    
    // Campos específicos de cachaça
    private String region;
    private BigDecimal alcoholContent;
    private Integer volume;
    private CachacaType cachacaType;
    private String cachacaTypeDisplayName;
    private AgingType agingType;
    private String agingTypeDisplayName;
    private Integer agingMonths;
    private String distillery;
    private Integer productionYear;
    
    /**
     * Converte uma entidade Cachaca para DTO.
     */
    public static CachacaResponseDTO fromEntity(Cachaca cachaca) {
        CachacaResponseDTO dto = new CachacaResponseDTO();
        dto.setId(cachaca.getId());
        dto.setName(cachaca.getName());
        dto.setDescription(cachaca.getDescription());
        dto.setPrice(cachaca.getPrice());
        dto.setImageUrl(cachaca.getImageUrl());
        dto.setSku(cachaca.getSku());
        dto.setCategory(cachaca.getCategory());
        dto.setActive(cachaca.getActive());
        dto.setRegion(cachaca.getRegion());
        dto.setAlcoholContent(cachaca.getAlcoholContent());
        dto.setVolume(cachaca.getVolume());
        dto.setCachacaType(cachaca.getCachacaType());
        dto.setCachacaTypeDisplayName(cachaca.getCachacaType() != null 
                ? cachaca.getCachacaType().getDisplayName() : null);
        dto.setAgingType(cachaca.getAgingType());
        dto.setAgingTypeDisplayName(cachaca.getAgingType() != null 
                ? cachaca.getAgingType().getDisplayName() : null);
        dto.setAgingMonths(cachaca.getAgingMonths());
        dto.setDistillery(cachaca.getDistillery());
        dto.setProductionYear(cachaca.getProductionYear());
        return dto;
    }
}
