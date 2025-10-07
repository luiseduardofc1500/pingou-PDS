package com.pds.pingou.produto.cachaca;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CachacaResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String urlImagem;
    private Boolean ativo;
    private String regiao;
    private BigDecimal teorAlcoolico;
    private Integer volume;
    private TipoCachaca tipoCachaca;
    private TipoEnvelhecimento tipoEnvelhecimento;
    private Integer tempoEnvelhecimentoMeses;
    private Integer anoProducao;
}