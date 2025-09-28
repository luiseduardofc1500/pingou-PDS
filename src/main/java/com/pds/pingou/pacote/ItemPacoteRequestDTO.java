package com.pds.pingou.pacote;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPacoteRequestDTO {
    private Long produtoId;
    private Integer quantidade;
    private String observacoes;
}