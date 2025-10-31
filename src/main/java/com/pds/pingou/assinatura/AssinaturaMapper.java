package com.pds.pingou.assinatura;

import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.security.user.User;

public class AssinaturaMapper {
    public static AssinaturaResponseDTO toDTO(Assinatura assinatura) {
        AssinaturaResponseDTO dto = new AssinaturaResponseDTO();
        dto.setId(assinatura.getId());
        dto.setUserId(assinatura.getUser().getId());
        dto.setPlanoId(assinatura.getPlano().getId());
        dto.setPlanoNome(assinatura.getPlano().getNome());
        dto.setStatus(assinatura.getStatus());
        dto.setDataInicio(assinatura.getDataInicio());
        dto.setDataExpiracao(assinatura.getDataExpiracao());
        return dto;
    }

    public static Assinatura toEntity(AssinaturaRequestDTO dto, User user, Plano plano) {
        Assinatura assinatura = new Assinatura();
        assinatura.setUser(user);
        assinatura.setPlano(plano);
        assinatura.setStatus(StatusAssinatura.ATIVA);
        assinatura.setDataInicio(java.time.LocalDate.now());
        assinatura.setDataExpiracao(null);
        return assinatura;
    }
}
