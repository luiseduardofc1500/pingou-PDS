package com.pds.pingou.produto.cachaca;

/**
 * Enumeração que define os tipos/classificações de cachaça.
 * 
 * Esta enum categoriza as cachaças de acordo com sua aparência,
 * processo de produção e envelhecimento, seguindo padrões tradicionais
 * da cachaça brasileira.
 * 
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
public enum TipoCachaca {
    /** Cachaça transparente, não envelhecida */
    BRANCA("Branca/Prata"),
    
    /** Cachaça com coloração dourada, levemente envelhecida */
    OURO("Ouro"),
    
    /** Cachaça envelhecida em madeira */
    ENVELHECIDA("Envelhecida"),
    
    /** Cachaça de qualidade superior */
    PREMIUM("Premium"),
    
    /** Cachaça de qualidade excepcional */
    EXTRA_PREMIUM("Extra Premium");
    
    private final String descricao;
    
    /**
     * Construtor do enum.
     * 
     * @param descricao Descrição amigável do tipo de cachaça
     */
    TipoCachaca(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * Retorna a descrição amigável do tipo de cachaça.
     * 
     * @return Descrição do tipo de cachaça
     */
    public String getDescricao() {
        return descricao;
    }
}