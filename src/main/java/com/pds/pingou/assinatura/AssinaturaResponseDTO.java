package com.pds.pingou.assinatura;

import com.pds.pingou.enums.StatusAssinatura;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AssinaturaResponseDTO {
    private Long id;
    private Long userId;
    private Long planoId;
    private StatusAssinatura status;
    private LocalDate dataInicio;
    private LocalDate dataExpiracao;
}

