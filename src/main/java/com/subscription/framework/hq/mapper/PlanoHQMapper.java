package com.subscription.framework.hq.mapper;

import com.subscription.framework.hq.domain.PlanoHQ;
import com.subscription.framework.hq.dto.PlanoHQResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PlanoHQMapper {

    public PlanoHQResponseDTO toDTO(PlanoHQ plano) {
        if (plano == null) {
            return null;
        }

        PlanoHQResponseDTO dto = new PlanoHQResponseDTO();
        dto.setId(plano.getId());
        dto.setName(plano.getName());
        dto.setDescription(plano.getDescription());
        dto.setPrice(plano.getPrice());
        dto.setMaxItemsPerDelivery(plano.getMaxItemsPerDelivery());
        dto.setDeliveryFrequency(plano.getDeliveryFrequency());
        dto.setPercentualClassicas(plano.getPercentualClassicas());
        dto.setPercentualModernas(plano.getPercentualModernas());
        dto.setIncluiEdicoesColecionador(plano.getIncluiEdicoesColecionador());
        dto.setMultiplicadorPontos(plano.getMultiplicadorPontos());
        dto.setFilosofiaCuradoria(plano.getFilosofiaCuradoria());
        dto.setPlanoColecionador(plano.getPlanoColecionador());
        dto.setActive(plano.getActive());

        return dto;
    }
}
