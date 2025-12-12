package com.subscription.framework.hq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PontosResponseDTO {
    
    private Long id;
    private Long userId;
    private Integer pontosTotais;
    private Integer pontosDisponiveis;
    private Integer pontosUtilizados;
    private Integer nivel;
    private Integer pontosParaProximoNivel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
