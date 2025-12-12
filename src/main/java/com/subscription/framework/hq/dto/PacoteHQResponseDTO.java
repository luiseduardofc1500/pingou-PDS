package com.subscription.framework.hq.dto;

import com.subscription.framework.api.dto.PackageItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacoteHQResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    private LocalDate deliveryDate;
    private Integer month;
    private Integer year;
    private Long planId;
    private String planName;
    private Long userId;
    private Integer totalPontos;
    private Integer quantidadeClassicas;
    private Integer quantidadeModernas;
    private Boolean curadoriaAutomatica;
    private Double notaCuradoria;
    private BigDecimal totalValue;
    private Boolean active;
    private List<PackageItemResponseDTO> items;
}
