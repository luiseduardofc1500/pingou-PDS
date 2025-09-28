package com.pds.pingou.pacote;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPacoteResponseDTO {
    private Long id;
    private Long produtoId;
    private String produtoNome;
    private BigDecimal produtoPreco;
    private String produtoImagem;
    private Integer quantidade;
    private String observacoes;
}