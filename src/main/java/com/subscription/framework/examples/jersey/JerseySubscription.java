package com.subscription.framework.examples.jersey;

import com.subscription.framework.core.domain.Subscription;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Assinatura específica para camisas de futebol.
 * Estende Subscription com preferências do torcedor/colecionador.
 */
@Entity
@Table(name = "jersey_subscriptions")
@DiscriminatorValue("JERSEY")
@Getter
@Setter
@NoArgsConstructor
public class JerseySubscription extends Subscription {
    
    /**
     * Time favorito do assinante
     */
    @Column(name = "favorite_team")
    private String favoriteTeam;
    
    /**
     * Segundo time favorito
     */
    @Column(name = "secondary_team")
    private String secondaryTeam;
    
    /**
     * Seleção nacional favorita
     */
    @Column(name = "favorite_national_team")
    private String favoriteNationalTeam;
    
    /**
     * Liga preferida (Brasileirão, Premier League, La Liga, etc.)
     */
    @Column(name = "preferred_league")
    private String preferredLeague;
    
    /**
     * Tamanho preferido
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_size")
    private JerseySize preferredSize;
    
    /**
     * Tipo de camisa preferido (Home, Away, Third)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_jersey_type")
    private JerseyType preferredJerseyType;
    
    /**
     * Indica se prefere camisas autênticas ou aceita réplicas
     */
    @Column(name = "prefers_authentic")
    private Boolean prefersAuthentic = false;
    
    /**
     * Jogador favorito (para personalização)
     */
    @Column(name = "favorite_player")
    private String favoritePlayer;
    
    /**
     * Número preferido (para personalização)
     */
    @Column(name = "preferred_number")
    private Integer preferredNumber;
    
    /**
     * Nome para personalização (se diferente do jogador)
     */
    @Column(name = "custom_name")
    private String customName;
    
    /**
     * Fabricante preferido
     */
    @Column(name = "preferred_manufacturer")
    private String preferredManufacturer;
    
    /**
     * Indica se aceita times rivais
     */
    @Column(name = "accepts_rival_teams")
    private Boolean acceptsRivalTeams = false;
    
    /**
     * Times a evitar (rivais, separados por vírgula)
     */
    @Column(name = "teams_to_avoid", length = 500)
    private String teamsToAvoid;
    
    /**
     * Indica se quer surpresas ou escolher
     */
    @Column(name = "surprise_mode")
    private Boolean surpriseMode = true;
    
    /**
     * Indica interesse em camisas retrô
     */
    @Column(name = "interested_in_retro")
    private Boolean interestedInRetro = false;
    
    /**
     * Década preferida para retrô (70, 80, 90, 2000)
     */
    @Column(name = "preferred_retro_decade")
    private Integer preferredRetroDecade;
    
    /**
     * Tipo de colecionador
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "collector_type")
    private CollectorType collectorType = CollectorType.FAN;
    
    /**
     * Indica se é para uso em jogos ou coleção
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "usage_purpose")
    private UsagePurpose usagePurpose = UsagePurpose.BOTH;
    
    /**
     * Verifica se tem preferências definidas
     */
    public boolean hasPreferences() {
        return favoriteTeam != null || preferredLeague != null || 
               preferredSize != null || favoritePlayer != null;
    }
    
    /**
     * Tipo de colecionador
     */
    public enum CollectorType {
        FAN("Torcedor", "Coleciona por paixão ao time"),
        CASUAL("Casual", "Gosta de camisas bonitas"),
        COLLECTOR("Colecionador", "Coleciona sistematicamente"),
        INVESTOR("Investidor", "Coleciona para valorização"),
        PLAYER("Jogador", "Usa para praticar esporte");
        
        private final String displayName;
        private final String description;
        
        CollectorType(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }
    
    /**
     * Propósito de uso da camisa
     */
    public enum UsagePurpose {
        COLLECTION("Coleção", "Apenas para guardar/expor"),
        WEAR("Vestir", "Para usar no dia a dia"),
        SPORTS("Esporte", "Para praticar esporte"),
        BOTH("Ambos", "Coleção e uso");
        
        private final String displayName;
        private final String description;
        
        UsagePurpose(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }
}
