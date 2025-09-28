package com.pds.pingou.produto.cachaca;

/**
 * Enumeração que define os tipos de envelhecimento de cachaça.
 * 
 * Esta enum categoriza os diferentes processos de envelhecimento
 * aplicados às cachaças, desde as não envelhecidas até as premium
 * com longos períodos de maturação.
 * 
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
public enum TipoEnvelhecimento {
    /** Cachaça sem processo de envelhecimento */
    SEM_ENVELHECIMENTO("Sem Envelhecimento"),
    
    /** Cachaça envelhecida por período padrão */
    ENVELHECIDA("Envelhecida"),
    
    /** Cachaça com envelhecimento estendido */
    EXTRA_ENVELHECIDA("Extra Envelhecida"),
    
    /** Cachaça com envelhecimento premium */
    PREMIUM("Premium");
    
    private final String descricao;
    
    /**
     * Construtor do enum.
     * 
     * @param descricao Descrição amigável do tipo de envelhecimento
     */
    TipoEnvelhecimento(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * Retorna a descrição amigável do tipo de envelhecimento.
     * 
     * @return Descrição do tipo de envelhecimento
     */
    public String getDescricao() {
        return descricao;
    }
}