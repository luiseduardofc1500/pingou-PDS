package com.subscription.framework.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisição de item de pacote.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Getter
@Setter
public class PackageItemRequestDTO {
    
    private Long productId;
    private Integer quantity = 1;
    private String notes;
}
