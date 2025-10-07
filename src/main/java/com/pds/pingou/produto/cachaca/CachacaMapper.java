package com.pds.pingou.produto.cachaca;

import org.springframework.stereotype.Component;

@Component
public class CachacaMapper {
    
    public static Cachaca toEntity(CachacaRequestDTO dto) {
        Cachaca cachaca = new Cachaca();
        cachaca.setNome(dto.getNome());
        cachaca.setDescricao(dto.getDescricao());
        cachaca.setPreco(dto.getPreco());
        cachaca.setUrlImagem(dto.getUrlImagem());
        cachaca.setRegiao(dto.getRegiao());
        cachaca.setTeorAlcoolico(dto.getTeorAlcoolico());
        cachaca.setVolume(dto.getVolume());
        cachaca.setTipoCachaca(dto.getTipoCachaca());
        cachaca.setTipoEnvelhecimento(dto.getTipoEnvelhecimento());
        cachaca.setTempoEnvelhecimentoMeses(dto.getTempoEnvelhecimentoMeses());
        cachaca.setAnoProducao(dto.getAnoProducao());
        return cachaca;
    }
    
    public static CachacaResponseDTO toDTO(Cachaca cachaca) {
        CachacaResponseDTO dto = new CachacaResponseDTO();
        dto.setId(cachaca.getId());
        dto.setNome(cachaca.getNome());
        dto.setDescricao(cachaca.getDescricao());
        dto.setPreco(cachaca.getPreco());
        dto.setUrlImagem(cachaca.getUrlImagem());
        dto.setAtivo(cachaca.getAtivo());
        dto.setRegiao(cachaca.getRegiao());
        dto.setTeorAlcoolico(cachaca.getTeorAlcoolico());
        dto.setVolume(cachaca.getVolume());
        dto.setTipoCachaca(cachaca.getTipoCachaca());
        dto.setTipoEnvelhecimento(cachaca.getTipoEnvelhecimento());
        dto.setTempoEnvelhecimentoMeses(cachaca.getTempoEnvelhecimentoMeses());
        dto.setAnoProducao(cachaca.getAnoProducao());
        return dto;
    }
    
    public static void updateEntity(Cachaca cachaca, CachacaRequestDTO dto) {
        cachaca.setNome(dto.getNome());
        cachaca.setDescricao(dto.getDescricao());
        cachaca.setPreco(dto.getPreco());
        cachaca.setUrlImagem(dto.getUrlImagem());
        cachaca.setRegiao(dto.getRegiao());
        cachaca.setTeorAlcoolico(dto.getTeorAlcoolico());
        cachaca.setVolume(dto.getVolume());
        cachaca.setTipoCachaca(dto.getTipoCachaca());
        cachaca.setTipoEnvelhecimento(dto.getTipoEnvelhecimento());
        cachaca.setTempoEnvelhecimentoMeses(dto.getTempoEnvelhecimentoMeses());
        cachaca.setAnoProducao(dto.getAnoProducao());
    }
}