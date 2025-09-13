package com.pds.pingou.planos;

public class PlanoMapper {
    public static Plano toEntity(PlanoRequestDTO dto) {
        Plano plano = new Plano();
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        return plano;
    }

    public static PlanoResponseDTO toDTO(Plano plano) {
        PlanoResponseDTO dto = new PlanoResponseDTO();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setDescricao(plano.getDescricao());
        dto.setPreco(plano.getPreco());
        return dto;
    }
}
