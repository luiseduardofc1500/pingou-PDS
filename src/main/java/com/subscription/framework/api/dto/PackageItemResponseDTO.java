package com.subscription.framework.api.dto;

import com.subscription.framework.core.domain.PackageItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para resposta de item de pacote.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Getter
@Setter
public class PackageItemResponseDTO {
    
    private Long id;
    private Long packageId;
    private Long productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String productImageUrl;
    private Integer quantity;
    private String notes;
    private BigDecimal subtotal;
    
    /**
     * Converte uma entidade PackageItem para DTO.
     */
    public static PackageItemResponseDTO fromEntity(PackageItem item) {
        PackageItemResponseDTO dto = new PackageItemResponseDTO();
        dto.setId(item.getId());
        dto.setPackageId(item.getPkg().getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setProductDescription(item.getProduct().getDescription());
        dto.setProductPrice(item.getProduct().getPrice());
        dto.setProductImageUrl(item.getProduct().getImageUrl());
        dto.setQuantity(item.getQuantity());
        dto.setNotes(item.getNotes());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }
}
