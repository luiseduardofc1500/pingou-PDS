package com.pds.pingou.planos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PlanoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
}
