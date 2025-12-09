package com.subscription.framework.api.dto;

import com.subscription.framework.core.domain.Package;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO para resposta de pacote.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Getter
@Setter
public class PackageResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    private LocalDate deliveryDate;
    private Integer month;
    private Integer year;
    private String formattedPeriod;
    private Long planId;
    private String planName;
    private Boolean active;
    private BigDecimal totalValue;
    private String theme;
    private Integer totalItemCount;
    private List<PackageItemResponseDTO> items;
    
    /**
     * Converte uma entidade Package para DTO.
     */
    public static PackageResponseDTO fromEntity(Package pkg) {
        PackageResponseDTO dto = new PackageResponseDTO();
        dto.setId(pkg.getId());
        dto.setName(pkg.getName());
        dto.setDescription(pkg.getDescription());
        dto.setDeliveryDate(pkg.getDeliveryDate());
        dto.setMonth(pkg.getMonth());
        dto.setYear(pkg.getYear());
        dto.setFormattedPeriod(pkg.getFormattedPeriod());
        dto.setPlanId(pkg.getPlan().getId());
        dto.setPlanName(pkg.getPlan().getName());
        dto.setActive(pkg.getActive());
        dto.setTotalValue(pkg.getTotalValue());
        dto.setTheme(pkg.getTheme());
        dto.setTotalItemCount(pkg.getTotalItemCount());
        dto.setItems(pkg.getItems().stream()
                .map(PackageItemResponseDTO::fromEntity)
                .toList());
        return dto;
    }
}
