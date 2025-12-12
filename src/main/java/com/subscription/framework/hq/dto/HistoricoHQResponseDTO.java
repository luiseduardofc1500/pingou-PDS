package com.subscription.framework.hq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoHQResponseDTO {
    
    private Long id;
    private Long userId;
    private QuadrinhoResponseDTO quadrinho;
    private Long packageId;
    private LocalDateTime dataEnvio;
    private Boolean lido;
    private Integer avaliacao;
    private String comentario;
}
