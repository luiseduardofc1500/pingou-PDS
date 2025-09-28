package com.pds.pingou.pacote;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PacoteResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataEntrega;
    private Integer mes;
    private Integer ano;
    private Long planoId;
    private String planoNome;
    private List<ItemPacoteResponseDTO> itens;
    private Boolean ativo;
}