package com.subscription.framework.examples.comics;

import com.subscription.framework.core.domain.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Plano específico para assinatura de HQs/Comics.
 * Estende Plan com características próprias do domínio de quadrinhos.
 */
@Entity
@Table(name = "comic_plans")
@DiscriminatorValue("COMIC")
@Getter
@Setter
@NoArgsConstructor
public class ComicPlan extends Plan {
    
    /**
     * Indica se o plano inclui capas variantes exclusivas
     */
    @Column(name = "includes_variant_covers")
    private Boolean includesVariantCovers = false;
    
    /**
     * Indica se o plano inclui edições exclusivas/limitadas
     */
    @Column(name = "includes_exclusives")
    private Boolean includesExclusives = false;
    
    /**
     * Indica se o plano inclui edições autografadas
     */
    @Column(name = "includes_signed_editions")
    private Boolean includesSignedEditions = false;
    
    /**
     * Indica se o plano inclui HQs de múltiplas editoras
     */
    @Column(name = "multi_publisher")
    private Boolean multiPublisher = true;
    
    /**
     * Editora preferencial (se single publisher)
     */
    @Column(name = "preferred_publisher")
    private String preferredPublisher;
    
    /**
     * Indica se inclui apenas lançamentos (ou também clássicos)
     */
    @Column(name = "new_releases_only")
    private Boolean newReleasesOnly = false;
    
    /**
     * Indica se o plano inclui graphic novels (além de issues)
     */
    @Column(name = "includes_graphic_novels")
    private Boolean includesGraphicNovels = false;
    
    /**
     * Indica se o plano inclui mangás
     */
    @Column(name = "includes_manga")
    private Boolean includesManga = false;
    
    /**
     * Gênero preferencial do plano
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_genre")
    private ComicGenre preferredGenre;
    
    /**
     * Indica se inclui merchandise/colecionáveis
     */
    @Column(name = "includes_collectibles")
    private Boolean includesCollectibles = false;
    
    /**
     * Indica se inclui pôsteres ou art prints
     */
    @Column(name = "includes_art_prints")
    private Boolean includesArtPrints = false;
    
    /**
     * Indica se inclui acesso a conteúdo digital
     */
    @Column(name = "includes_digital_access")
    private Boolean includesDigitalAccess = false;
    
    /**
     * Indica se inclui sleeve de proteção para as HQs
     */
    @Column(name = "includes_protective_sleeves")
    private Boolean includesProtectiveSleeves = true;
    
    /**
     * Desconto percentual em loja parceira
     */
    @Column(name = "store_discount_percent")
    private Integer storeDiscountPercent = 0;
    
    /**
     * Verifica se é um plano para colecionadores
     */
    public boolean isCollectorPlan() {
        return includesVariantCovers || includesExclusives || 
               includesSignedEditions || includesCollectibles;
    }
    
    /**
     * Retorna resumo dos benefícios
     */
    public String getBenefitsSummary() {
        StringBuilder benefits = new StringBuilder();
        
        if (includesVariantCovers) benefits.append("• Capas variantes exclusivas\n");
        if (includesExclusives) benefits.append("• Edições exclusivas/limitadas\n");
        if (includesSignedEditions) benefits.append("• Edições autografadas\n");
        if (includesGraphicNovels) benefits.append("• Graphic novels incluídas\n");
        if (includesManga) benefits.append("• Mangás incluídos\n");
        if (includesCollectibles) benefits.append("• Colecionáveis inclusos\n");
        if (includesArtPrints) benefits.append("• Art prints e pôsteres\n");
        if (includesDigitalAccess) benefits.append("• Acesso digital ao acervo\n");
        if (includesProtectiveSleeves) benefits.append("• Sleeves de proteção\n");
        if (storeDiscountPercent > 0) {
            benefits.append("• ").append(storeDiscountPercent).append("% desconto em loja\n");
        }
        
        return benefits.toString();
    }
}
