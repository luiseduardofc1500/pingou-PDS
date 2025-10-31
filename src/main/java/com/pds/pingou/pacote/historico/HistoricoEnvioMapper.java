package com.pds.pingou.pacote.historico;

public class HistoricoEnvioMapper {
    public static HistoricoEnvioDTO toDTO(HistoricoEnvio h) {
        HistoricoEnvioDTO dto = new HistoricoEnvioDTO();
        dto.setId(h.getId());
        dto.setUserId(h.getUser().getId());
        dto.setUserEmail(h.getUser().getEmail());
        dto.setUserNome(h.getUser().getNome());
        dto.setPacoteId(h.getPacote().getId());
        dto.setPlanoId(h.getPacote().getPlano().getId());
        dto.setPlanoNome(h.getPacote().getPlano().getNome());
        dto.setProdutoId(h.getProduto().getId());
        dto.setProdutoNome(h.getProduto().getNome());
        dto.setQuantidade(h.getQuantidade());
        dto.setDataEnvio(h.getDataEnvio());
        return dto;
    }
}


