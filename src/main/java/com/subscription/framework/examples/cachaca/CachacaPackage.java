package com.subscription.framework.examples.cachaca;

import com.subscription.framework.core.domain.Package;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pacote específico para assinatura de cachaças.
 * Estende Package com informações próprias do domínio de bebidas.
 */
@Entity
@Table(name = "cachaca_packages")
@DiscriminatorValue("CACHACA")
@Getter
@Setter
@NoArgsConstructor
public class CachacaPackage extends Package {
    
    /**
     * Tema de harmonização do mês (ex: "Caipirinha Gourmet", "Queijos Mineiros")
     */
    @Column(name = "harmonization_theme", length = 500)
    private String harmonizationTheme;
    
    /**
     * Sugestão de harmonização detalhada
     */
    @Column(name = "harmonization_suggestion", length = 2000)
    private String harmonizationSuggestion;
    
    /**
     * Região em destaque no pacote do mês
     */
    @Column(name = "featured_region")
    private String featuredRegion;
    
    /**
     * Tipo de cachaça em destaque no pacote
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "featured_cachaca_type")
    private CachacaType featuredCachacaType;
    
    /**
     * Indica se o pacote inclui cachaça de edição limitada
     */
    @Column(name = "has_limited_edition")
    private Boolean hasLimitedEdition = false;
    
    /**
     * Indica se o pacote inclui cachaça premiada
     */
    @Column(name = "has_awarded_cachaca")
    private Boolean hasAwardedCachaca = false;
    
    /**
     * Nome do prêmio, se houver cachaça premiada
     */
    @Column(name = "award_name")
    private String awardName;
    
    /**
     * Volume total em ml de todas as garrafas do pacote
     */
    @Column(name = "total_volume_ml")
    private Integer totalVolumeMl;
    
    /**
     * Teor alcoólico médio das cachaças do pacote
     */
    @Column(name = "average_alcohol_content")
    private Double averageAlcoholContent;
    
    /**
     * Link do vídeo de degustação (YouTube, Vimeo, etc.)
     */
    @Column(name = "tasting_video_url")
    private String tastingVideoUrl;
    
    /**
     * Link do PDF com fichas técnicas
     */
    @Column(name = "tasting_notes_pdf_url")
    private String tastingNotesPdfUrl;
    
    /**
     * Receita de drink sugerida para o pacote
     */
    @Column(name = "drink_recipe", length = 2000)
    private String drinkRecipe;
    
    /**
     * Nome do drink sugerido
     */
    @Column(name = "drink_name")
    private String drinkName;
    
    /**
     * Curiosidade ou história sobre uma das cachaças
     */
    @Column(name = "curiosity_text", length = 2000)
    private String curiosityText;
    
    /**
     * Indica se o pacote inclui acessório de brinde
     */
    @Column(name = "includes_gift")
    private Boolean includesGift = false;
    
    /**
     * Descrição do brinde, se houver
     */
    @Column(name = "gift_description")
    private String giftDescription;
    
    /**
     * Data da sessão de degustação online, se houver
     */
    @Column(name = "tasting_session_date")
    private java.time.LocalDateTime tastingSessionDate;
    
    /**
     * Link da sessão de degustação online
     */
    @Column(name = "tasting_session_link")
    private String tastingSessionLink;
    
    /**
     * Calcula o volume total do pacote baseado nos itens
     */
    public void calculateTotalVolume() {
        int total = getItems().stream()
                .filter(item -> item.getProduct() instanceof Cachaca)
                .mapToInt(item -> {
                    Cachaca cachaca = (Cachaca) item.getProduct();
                    return (cachaca.getVolume() != null ? cachaca.getVolume() : 0) * item.getQuantity();
                })
                .sum();
        this.totalVolumeMl = total;
    }
    
    /**
     * Calcula o teor alcoólico médio do pacote
     */
    public void calculateAverageAlcoholContent() {
        double avg = getItems().stream()
                .filter(item -> item.getProduct() instanceof Cachaca)
                .mapToDouble(item -> {
                    Cachaca cachaca = (Cachaca) item.getProduct();
                    return cachaca.getAlcoholContent() != null ? cachaca.getAlcoholContent().doubleValue() : 0.0;
                })
                .average()
                .orElse(0.0);
        this.averageAlcoholContent = avg;
    }
    
    /**
     * Verifica se o pacote tem conteúdo educacional
     */
    public boolean hasEducationalContent() {
        return tastingVideoUrl != null || tastingNotesPdfUrl != null || curiosityText != null;
    }
}
