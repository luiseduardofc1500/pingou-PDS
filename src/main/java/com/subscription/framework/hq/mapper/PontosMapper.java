package com.subscription.framework.hq.mapper;

import com.subscription.framework.hq.domain.UsuarioPontos;
import com.subscription.framework.hq.dto.PontosResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PontosMapper {

    public PontosResponseDTO toDTO(UsuarioPontos pontos) {
        if (pontos == null) {
            return null;
        }

        PontosResponseDTO dto = new PontosResponseDTO();
        dto.setId(pontos.getId());
        dto.setUserId(pontos.getUserId());
        dto.setPontosTotais(pontos.getPontosTotais());
        dto.setPontosDisponiveis(pontos.getPontosDisponiveis());
        dto.setPontosUtilizados(pontos.getPontosUtilizados());
        dto.setNivel(pontos.getNivel());
        dto.setPontosParaProximoNivel(pontos.pontosParaProximoNivel());
        dto.setCreatedAt(pontos.getCreatedAt());
        dto.setUpdatedAt(pontos.getUpdatedAt());

        return dto;
    }
}
