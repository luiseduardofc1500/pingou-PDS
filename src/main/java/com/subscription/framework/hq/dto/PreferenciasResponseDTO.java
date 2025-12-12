package com.subscription.framework.hq.dto;

import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenciasResponseDTO {
    
    private Long id;
    private Long userId;
    private Set<CategoriaHQ> categoriasFavoritas;
    private Set<Editora> editorasFavoritas;
    private Integer preferenciaClassicas;
    private Boolean interesseEdicoesColecionador;
    private Set<String> seriesAcompanhadas;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean onboardingCompleto;
}
