package com.subscription.framework.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO para requisição de criação/atualização de pacote.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Getter
@Setter
public class PackageRequestDTO {
    
    private String name;
    private String description;
    private LocalDate deliveryDate;
    private Integer month;
    private Integer year;
    private Long planId;
    private String theme;
    private List<PackageItemRequestDTO> items;
}
