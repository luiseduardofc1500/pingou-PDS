package com.subscription.framework.examples.cachaca;

import com.subscription.framework.core.domain.Subscription;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Assinatura específica para cachaças.
 * Estende Subscription com preferências do assinante no domínio de bebidas.
 */
@Entity
@Table(name = "cachaca_subscriptions")
@DiscriminatorValue("CACHACA")
@Getter
@Setter
@NoArgsConstructor
public class CachacaSubscription extends Subscription {
    
    /**
     * Tipo de cachaça preferido pelo assinante
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_type")
    private CachacaType preferredType;
    
    /**
     * Tipo de envelhecimento preferido
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_aging")
    private AgingType preferredAging;
    
    /**
     * Região de origem preferida
     */
    @Column(name = "preferred_region")
    private String preferredRegion;
    
    /**
     * Teor alcoólico mínimo preferido
     */
    @Column(name = "min_alcohol_content")
    private Double minAlcoholContent;
    
    /**
     * Teor alcoólico máximo preferido
     */
    @Column(name = "max_alcohol_content")
    private Double maxAlcoholContent;
    
    /**
     * Volume preferido por garrafa em ml
     */
    @Column(name = "preferred_volume")
    private Integer preferredVolume;
    
    /**
     * Indica se o assinante aceita repetição de cachaças
     */
    @Column(name = "accepts_repeats")
    private Boolean acceptsRepeats = true;
    
    /**
     * Indica se o assinante quer surpresas ou escolher
     */
    @Column(name = "surprise_mode")
    private Boolean surpriseMode = true;
    
    /**
     * Notas de preferência do assinante (texto livre)
     */
    @Column(name = "tasting_preferences", length = 1000)
    private String tastingPreferences;
    
    /**
     * Nível de conhecimento sobre cachaça (INICIANTE, INTERMEDIARIO, EXPERT)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "expertise_level")
    private ExpertiseLevel expertiseLevel = ExpertiseLevel.INICIANTE;
    
    /**
     * Indica se tem interesse em aprender sobre cachaça
     */
    @Column(name = "interested_in_learning")
    private Boolean interestedInLearning = true;
    
    /**
     * Frequência de consumo (vezes por semana)
     */
    @Column(name = "consumption_frequency")
    private Integer consumptionFrequency;
    
    /**
     * Indica se o assinante possui restrições alimentares
     */
    @Column(name = "dietary_restrictions")
    private String dietaryRestrictions;
    
    /**
     * Verifica se o assinante tem preferências definidas
     */
    public boolean hasPreferences() {
        return preferredType != null || preferredAging != null || 
               preferredRegion != null || tastingPreferences != null;
    }
    
    /**
     * Nível de experiência do assinante
     */
    public enum ExpertiseLevel {
        INICIANTE("Iniciante", "Está começando a conhecer cachaças"),
        INTERMEDIARIO("Intermediário", "Conhece diferentes tipos e regiões"),
        AVANCADO("Avançado", "Conhecedor experiente"),
        EXPERT("Expert", "Sommelier ou profundo conhecedor");
        
        private final String displayName;
        private final String description;
        
        ExpertiseLevel(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }
}
