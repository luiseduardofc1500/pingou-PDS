package com.subscription.framework.examples.cachaca;

import com.subscription.framework.core.domain.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Plano específico para assinatura de cachaças.
 * Estende Plan com características próprias do domínio de bebidas.
 */
@Entity
@Table(name = "cachaca_plans")
@DiscriminatorValue("CACHACA")
@Getter
@Setter
@NoArgsConstructor
public class CachacaPlan extends Plan {
    
    /**
     * Indica se o plano inclui degustação guiada online
     */
    @Column(name = "includes_tasting_session")
    private Boolean includesTastingSession = false;
    
    /**
     * Indica se o plano oferece acesso a sommelier exclusivo
     */
    @Column(name = "includes_sommelier_access")
    private Boolean includesSommelierAccess = false;
    
    /**
     * Indica se o plano inclui cachaças de edição limitada
     */
    @Column(name = "includes_limited_editions")
    private Boolean includesLimitedEditions = false;
    
    /**
     * Indica se o plano inclui cachaças envelhecidas (extra premium)
     */
    @Column(name = "includes_aged_only")
    private Boolean includesAgedOnly = false;
    
    /**
     * Indica se o plano inclui ficha técnica detalhada das cachaças
     */
    @Column(name = "includes_tasting_notes")
    private Boolean includesTastingNotes = true;
    
    /**
     * Indica se o plano inclui desconto em loja parceira
     */
    @Column(name = "partner_store_discount_percent")
    private Integer partnerStoreDiscountPercent = 0;
    
    /**
     * Região de origem preferencial (ex: Minas Gerais, Nordeste)
     * Se null, inclui cachaças de todas as regiões
     */
    @Column(name = "preferred_region")
    private String preferredRegion;
    
    /**
     * Tipo preferencial de cachaça (ARTESANAL, INDUSTRIAL, etc.)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_cachaca_type")
    private CachacaType preferredCachacaType;
    
    /**
     * Volume mínimo por garrafa em ml (ex: 700ml apenas)
     */
    @Column(name = "minimum_bottle_volume")
    private Integer minimumBottleVolume;
    
    /**
     * Indica se inclui acessórios (copos, dosadores, etc.)
     */
    @Column(name = "includes_accessories")
    private Boolean includesAccessories = false;
    
    /**
     * Verifica se o plano é considerado premium
     */
    public boolean isPremium() {
        return includesSommelierAccess || includesLimitedEditions || includesAgedOnly;
    }
    
    /**
     * Retorna descrição dos benefícios do plano
     */
    public String getBenefitsSummary() {
        StringBuilder benefits = new StringBuilder();
        
        if (includesTastingSession) benefits.append("• Degustação guiada online\n");
        if (includesSommelierAccess) benefits.append("• Acesso a sommelier exclusivo\n");
        if (includesLimitedEditions) benefits.append("• Edições limitadas\n");
        if (includesAgedOnly) benefits.append("• Apenas cachaças envelhecidas\n");
        if (includesTastingNotes) benefits.append("• Fichas técnicas detalhadas\n");
        if (includesAccessories) benefits.append("• Acessórios inclusos\n");
        if (partnerStoreDiscountPercent > 0) {
            benefits.append("• ").append(partnerStoreDiscountPercent).append("% desconto em loja parceira\n");
        }
        
        return benefits.toString();
    }
}
