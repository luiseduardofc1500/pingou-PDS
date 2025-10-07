package com.pds.pingou.planos;

public class PlanoMapper {
    public static Plano toEntity(PlanoRequestDTO dto) {
        Plano plano = new Plano();
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        plano.setMaxProdutosPorMes(dto.getMaxProdutosPorMes());
        plano.setFrequenciaEntrega(dto.getFrequenciaEntrega());
        return plano;
    }

    public static PlanoResponseDTO toDTO(Plano plano) {
        PlanoResponseDTO dto = new PlanoResponseDTO();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setDescricao(plano.getDescricao());
        dto.setPreco(plano.getPreco());
        dto.setMaxProdutosPorMes(plano.getMaxProdutosPorMes());
        dto.setFrequenciaEntrega(plano.getFrequenciaEntrega());
        dto.setAtivo(plano.getAtivo());
        return dto;
    }
}
