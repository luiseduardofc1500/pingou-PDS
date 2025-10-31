package com.pds.pingou.AI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratePackageRequestDTO {
    @NotNull
    private Long planoId;

    @Min(1)
    private Integer tamanho; // opcional; se ausente, usar maxProdutosPorMes do plano
}


