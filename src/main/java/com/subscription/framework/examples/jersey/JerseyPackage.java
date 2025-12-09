package com.subscription.framework.examples.jersey;

import com.subscription.framework.core.domain.Package;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Pacote específico para assinatura de camisas de futebol.
 * Estende Package com informações próprias do domínio esportivo.
 */
@Entity
@Table(name = "jersey_packages")
@DiscriminatorValue("JERSEY")
@Getter
@Setter
@NoArgsConstructor
public class JerseyPackage extends Package {
    
    /**
     * Temporada em destaque (ex: "2024/2025")
     */
    @Column(name = "featured_season")
    private String featuredSeason;
    
    /**
     * Liga em destaque no pacote
     */
    @Column(name = "featured_league")
    private String featuredLeague;
    
    /**
     * Time em destaque no pacote
     */
    @Column(name = "featured_team")
    private String featuredTeam;
    
    /**
     * Jogador em destaque (ícone/lenda)
     */
    @Column(name = "featured_player")
    private String featuredPlayer;
    
    /**
     * Fabricante em destaque
     */
    @Column(name = "featured_manufacturer")
    private String featuredManufacturer;
    
    /**
     * Evento relacionado (Copa do Mundo, Champions, etc.)
     */
    @Column(name = "related_event")
    private String relatedEvent;
    
    /**
     * Data do evento relacionado
     */
    @Column(name = "event_date")
    private LocalDate eventDate;
    
    /**
     * Indica se o pacote inclui camisa retrô
     */
    @Column(name = "has_retro_jersey")
    private Boolean hasRetroJersey = false;
    
    /**
     * Década da camisa retrô
     */
    @Column(name = "retro_decade")
    private Integer retroDecade;
    
    /**
     * Indica se o pacote inclui camisa de edição especial
     */
    @Column(name = "has_special_edition")
    private Boolean hasSpecialEdition = false;
    
    /**
     * Motivo da edição especial
     */
    @Column(name = "special_edition_reason")
    private String specialEditionReason;
    
    /**
     * Indica se inclui camisa autêntica (não réplica)
     */
    @Column(name = "has_authentic")
    private Boolean hasAuthentic = false;
    
    /**
     * Indica se inclui acessório (meias, shorts, etc.)
     */
    @Column(name = "has_accessory")
    private Boolean hasAccessory = false;
    
    /**
     * Descrição do acessório
     */
    @Column(name = "accessory_description")
    private String accessoryDescription;
    
    /**
     * Indica se inclui item colecionável (pin, patch, etc.)
     */
    @Column(name = "has_collectible")
    private Boolean hasCollectible = false;
    
    /**
     * Descrição do colecionável
     */
    @Column(name = "collectible_description")
    private String collectibleDescription;
    
    /**
     * Curiosidade ou história sobre a camisa/time
     */
    @Column(name = "jersey_story", length = 2000)
    private String jerseyStory;
    
    /**
     * Momento histórico relacionado (gol, título, etc.)
     */
    @Column(name = "historic_moment", length = 1000)
    private String historicMoment;
    
    /**
     * Link para documentário ou vídeo relacionado
     */
    @Column(name = "video_content_url")
    private String videoContentUrl;
    
    /**
     * Número total de camisas no pacote
     */
    @Column(name = "jersey_count")
    private Integer jerseyCount;
    
    /**
     * Indica se é um pacote de derby/clássico (times rivais)
     */
    @Column(name = "is_derby_package")
    private Boolean isDerbyPackage = false;
    
    /**
     * Times do derby, se aplicável
     */
    @Column(name = "derby_teams")
    private String derbyTeams;
    
    /**
     * Indica se é um pacote temático de Copa do Mundo
     */
    @Column(name = "is_world_cup_themed")
    private Boolean isWorldCupThemed = false;
    
    /**
     * Ano da Copa do Mundo temática
     */
    @Column(name = "world_cup_year")
    private Integer worldCupYear;
    
    /**
     * Conta o número de camisas no pacote
     */
    public void calculateJerseyCount() {
        this.jerseyCount = getItems().stream()
                .filter(item -> item.getProduct() instanceof SoccerJersey)
                .mapToInt(item -> item.getQuantity())
                .sum();
    }
    
    /**
     * Verifica se o pacote é premium (tem extras)
     */
    public boolean isPremiumPackage() {
        return hasRetroJersey || hasSpecialEdition || hasAuthentic || hasCollectible;
    }
    
    /**
     * Verifica se o pacote tem conteúdo educacional/histórico
     */
    public boolean hasHistoricalContent() {
        return jerseyStory != null || historicMoment != null || videoContentUrl != null;
    }
    
    /**
     * Retorna descrição resumida do pacote
     */
    public String getPackageHighlights() {
        StringBuilder highlights = new StringBuilder();
        
        if (featuredTeam != null) highlights.append("⚽ Time: ").append(featuredTeam).append("\n");
        if (featuredLeague != null) highlights.append("🏆 Liga: ").append(featuredLeague).append("\n");
        if (featuredSeason != null) highlights.append("📅 Temporada: ").append(featuredSeason).append("\n");
        if (hasRetroJersey) highlights.append("🕰️ Inclui camisa retrô\n");
        if (hasSpecialEdition) highlights.append("⭐ Edição especial\n");
        if (hasAuthentic) highlights.append("✅ Camisa autêntica\n");
        if (relatedEvent != null) highlights.append("🎯 Evento: ").append(relatedEvent).append("\n");
        
        return highlights.toString();
    }
}
