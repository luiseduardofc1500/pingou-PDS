package com.subscription.framework.hq.mapper;

import com.subscription.framework.hq.domain.UsuarioPreferencias;
import com.subscription.framework.hq.dto.PreferenciasResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PreferenciasMapper {

    public PreferenciasResponseDTO toDTO(UsuarioPreferencias preferencias) {
        if (preferencias == null) {
            return null;
        }

        PreferenciasResponseDTO dto = new PreferenciasResponseDTO();
        dto.setId(preferencias.getId());
        dto.setUserId(preferencias.getUserId());
        dto.setCategoriasFavoritas(preferencias.getCategoriasFavoritas());
        dto.setEditorasFavoritas(preferencias.getEditorasFavoritas());
        dto.setPreferenciaClassicas(preferencias.getPreferenciaClassicas());
        dto.setInteresseEdicoesColecionador(preferencias.getInteresseEdicoesColecionador());
        dto.setSeriesAcompanhadas(preferencias.getSeriesAcompanhadas());
        dto.setCreatedAt(preferencias.getCreatedAt());
        dto.setUpdatedAt(preferencias.getUpdatedAt());
        dto.setOnboardingCompleto(preferencias.getOnboardingCompleto());

        return dto;
    }
}
