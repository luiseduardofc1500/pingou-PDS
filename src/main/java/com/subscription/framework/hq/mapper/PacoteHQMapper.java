package com.subscription.framework.hq.mapper;

import com.subscription.framework.api.dto.PackageItemResponseDTO;
import com.subscription.framework.core.domain.PackageItem;
import com.subscription.framework.hq.domain.PacoteHQ;
import com.subscription.framework.hq.dto.PacoteHQResponseDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PacoteHQMapper {

    public PacoteHQResponseDTO toDTO(PacoteHQ pacote) {
        if (pacote == null) {
            return null;
        }

        PacoteHQResponseDTO dto = new PacoteHQResponseDTO();
        dto.setId(pacote.getId());
        dto.setName(pacote.getName());
        dto.setDescription(pacote.getDescription());
        dto.setDeliveryDate(pacote.getDeliveryDate());
        dto.setMonth(pacote.getMonth());
        dto.setYear(pacote.getYear());
        dto.setPlanId(pacote.getPlan().getId());
        dto.setPlanName(pacote.getPlan().getName());
        dto.setUserId(pacote.getUserId());
        dto.setTotalPontos(pacote.getTotalPontos());
        dto.setQuantidadeClassicas(pacote.getQuantidadeClassicas());
        dto.setQuantidadeModernas(pacote.getQuantidadeModernas());
        dto.setCuradoriaAutomatica(pacote.getCuradoriaAutomatica());
        dto.setNotaCuradoria(pacote.getNotaCuradoria());
        dto.setTotalValue(pacote.getTotalValue());
        dto.setActive(pacote.getActive());

        // Mapeia os itens
        if (pacote.getItems() != null) {
            dto.setItems(pacote.getItems().stream()
                    .map(this::mapPackageItem)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private PackageItemResponseDTO mapPackageItem(PackageItem item) {
        PackageItemResponseDTO dto = new PackageItemResponseDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
