package com.subscription.framework.hq.mapper;

import com.subscription.framework.hq.domain.UsuarioHistoricoHQ;
import com.subscription.framework.hq.dto.HistoricoHQResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoricoHQMapper {

    private final QuadrinhoMapper quadrinhoMapper;

    public HistoricoHQResponseDTO toDTO(UsuarioHistoricoHQ historico) {
        if (historico == null) {
            return null;
        }

        HistoricoHQResponseDTO dto = new HistoricoHQResponseDTO();
        dto.setId(historico.getId());
        dto.setUserId(historico.getUserId());
        dto.setQuadrinho(quadrinhoMapper.toDTO(historico.getQuadrinho()));
        dto.setPackageId(historico.getPackageId());
        dto.setDataEnvio(historico.getDataEnvio());
        dto.setLido(historico.getLido());
        dto.setAvaliacao(historico.getAvaliacao());
        dto.setComentario(historico.getComentario());

        return dto;
    }
}
