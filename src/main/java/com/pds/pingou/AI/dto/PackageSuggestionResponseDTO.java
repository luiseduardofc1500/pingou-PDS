package com.pds.pingou.AI.dto;

import com.pds.pingou.pacote.ItemPacoteRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackageSuggestionResponseDTO {
    private Long planoId;
    private Integer tamanho;
    private List<ItemPacoteRequestDTO> itens;
}


