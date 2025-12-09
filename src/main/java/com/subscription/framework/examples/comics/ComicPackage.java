package com.subscription.framework.examples.comics;

import com.subscription.framework.core.domain.Package;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pacote específico para assinatura de HQs/Comics.
 * Estende Package com informações próprias do universo de quadrinhos.
 */
@Entity
@Table(name = "comic_packages")
@DiscriminatorValue("COMIC")
@Getter
@Setter
@NoArgsConstructor
public class ComicPackage extends Package {
    
    /**
     * Tema do mês (ex: "Vilões", "Mulheres nos Quadrinhos", "Cosmic Marvel")
     */
    @Column(name = "monthly_theme", length = 200)
    private String monthlyTheme;
    
    /**
     * Descrição do tema
     */
    @Column(name = "theme_description", length = 2000)
    private String themeDescription;
    
    /**
     * Personagem em destaque do mês
     */
    @Column(name = "featured_character")
    private String featuredCharacter;
    
    /**
     * Editora em destaque do mês
     */
    @Column(name = "featured_publisher")
    private String featuredPublisher;
    
    /**
     * Série/arco em destaque
     */
    @Column(name = "featured_series")
    private String featuredSeries;
    
    /**
     * Indica se o pacote inclui capa variante
     */
    @Column(name = "has_variant_cover")
    private Boolean hasVariantCover = false;
    
    /**
     * Descrição da capa variante
     */
    @Column(name = "variant_cover_description")
    private String variantCoverDescription;
    
    /**
     * Indica se o pacote inclui edição autografada
     */
    @Column(name = "has_signed_edition")
    private Boolean hasSignedEdition = false;
    
    /**
     * Nome de quem autografou
     */
    @Column(name = "signed_by")
    private String signedBy;
    
    /**
     * Indica se inclui colecionável/merchandise
     */
    @Column(name = "has_collectible")
    private Boolean hasCollectible = false;
    
    /**
     * Descrição do colecionável
     */
    @Column(name = "collectible_description")
    private String collectibleDescription;
    
    /**
     * Indica se inclui pôster/art print
     */
    @Column(name = "has_art_print")
    private Boolean hasArtPrint = false;
    
    /**
     * Descrição do art print
     */
    @Column(name = "art_print_description")
    private String artPrintDescription;
    
    /**
     * Total de páginas somando todas as HQs
     */
    @Column(name = "total_pages")
    private Integer totalPages;
    
    /**
     * Número de issues/edições no pacote
     */
    @Column(name = "issue_count")
    private Integer issueCount;
    
    /**
     * Curiosidade ou trivia sobre o tema do mês
     */
    @Column(name = "trivia", length = 2000)
    private String trivia;
    
    /**
     * Recomendação de leitura relacionada
     */
    @Column(name = "reading_recommendation", length = 1000)
    private String readingRecommendation;
    
    /**
     * Link para conteúdo digital complementar
     */
    @Column(name = "digital_content_url")
    private String digitalContentUrl;
    
    /**
     * Link para playlist temática (Spotify, YouTube)
     */
    @Column(name = "themed_playlist_url")
    private String themedPlaylistUrl;
    
    /**
     * Evento relacionado (lançamento de filme, série, etc.)
     */
    @Column(name = "related_event")
    private String relatedEvent;
    
    /**
     * Data do evento relacionado
     */
    @Column(name = "event_date")
    private java.time.LocalDate eventDate;
    
    /**
     * Indica se é um pacote de edição especial (aniversário, etc.)
     */
    @Column(name = "is_special_edition")
    private Boolean isSpecialEdition = false;
    
    /**
     * Motivo da edição especial
     */
    @Column(name = "special_edition_reason")
    private String specialEditionReason;
    
    /**
     * Calcula o total de páginas do pacote
     */
    public void calculateTotalPages() {
        int total = getItems().stream()
                .filter(item -> item.getProduct() instanceof Comic)
                .mapToInt(item -> {
                    Comic comic = (Comic) item.getProduct();
                    return (comic.getPageCount() != null ? comic.getPageCount() : 0) * item.getQuantity();
                })
                .sum();
        this.totalPages = total;
    }
    
    /**
     * Conta o número de issues no pacote
     */
    public void calculateIssueCount() {
        this.issueCount = getItems().stream()
                .filter(item -> item.getProduct() instanceof Comic)
                .mapToInt(item -> item.getQuantity())
                .sum();
    }
    
    /**
     * Verifica se o pacote é premium (tem extras)
     */
    public boolean isPremiumPackage() {
        return hasVariantCover || hasSignedEdition || hasCollectible || hasArtPrint;
    }
    
    /**
     * Verifica se o pacote tem conteúdo digital
     */
    public boolean hasDigitalContent() {
        return digitalContentUrl != null || themedPlaylistUrl != null;
    }
}
