package com.pds.pingou.planos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PlanoRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer maxProdutosPorMes;
    private String frequenciaEntrega = "MENSAL";
}
