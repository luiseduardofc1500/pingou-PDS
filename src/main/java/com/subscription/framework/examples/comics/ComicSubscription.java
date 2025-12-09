package com.subscription.framework.examples.comics;

import com.subscription.framework.core.domain.Subscription;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Assinatura específica para HQs/Comics.
 * Estende Subscription com preferências do leitor de quadrinhos.
 */
@Entity
@Table(name = "comic_subscriptions")
@DiscriminatorValue("COMIC")
@Getter
@Setter
@NoArgsConstructor
public class ComicSubscription extends Subscription {
    
    /**
     * Gênero preferido de HQs
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_genre")
    private ComicGenre preferredGenre;
    
    /**
     * Formato preferido
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_format")
    private ComicFormat preferredFormat;
    
    /**
     * Editora favorita
     */
    @Column(name = "favorite_publisher")
    private String favoritePublisher;
    
    /**
     * Personagens favoritos (separados por vírgula)
     */
    @Column(name = "favorite_characters", length = 1000)
    private String favoriteCharacters;
    
    /**
     * Escritores favoritos (separados por vírgula)
     */
    @Column(name = "favorite_writers", length = 500)
    private String favoriteWriters;
    
    /**
     * Artistas favoritos (separados por vírgula)
     */
    @Column(name = "favorite_artists", length = 500)
    private String favoriteArtists;
    
    /**
     * Séries que o assinante já acompanha (para evitar duplicatas)
     */
    @Column(name = "current_series", length = 2000)
    private String currentSeries;
    
    /**
     * Indica se aceita HQs que já possui (colecionador de variantes)
     */
    @Column(name = "accepts_duplicates")
    private Boolean acceptsDuplicates = false;
    
    /**
     * Indica se prefere surpresas ou escolher
     */
    @Column(name = "surprise_mode")
    private Boolean surpriseMode = true;
    
    /**
     * Indica interesse em mangás
     */
    @Column(name = "interested_in_manga")
    private Boolean interestedInManga = false;
    
    /**
     * Indica interesse em HQs nacionais
     */
    @Column(name = "interested_in_national")
    private Boolean interestedInNational = false;
    
    /**
     * Indica interesse em HQs independentes
     */
    @Column(name = "interested_in_indie")
    private Boolean interestedInIndie = false;
    
    /**
     * Nível de leitor
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "reader_level")
    private ReaderLevel readerLevel = ReaderLevel.CASUAL;
    
    /**
     * Idade preferida das HQs (ALL_AGES, TEEN, MATURE)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "age_preference")
    private AgeRating agePreference = AgeRating.TEEN;
    
    /**
     * Idioma preferido
     */
    @Column(name = "preferred_language")
    private String preferredLanguage = "pt-BR";
    
    /**
     * Indica se colecionador quer slabs (HQs encapsuladas/graded)
     */
    @Column(name = "interested_in_slabs")
    private Boolean interestedInSlabs = false;
    
    /**
     * Verifica se tem preferências definidas
     */
    public boolean hasPreferences() {
        return preferredGenre != null || favoritePublisher != null || 
               favoriteCharacters != null || preferredFormat != null;
    }
    
    /**
     * Nível de leitura do assinante
     */
    public enum ReaderLevel {
        NOVICE("Novato", "Está começando a ler HQs"),
        CASUAL("Casual", "Lê ocasionalmente"),
        REGULAR("Regular", "Lê frequentemente"),
        COLLECTOR("Colecionador", "Coleciona e preserva"),
        HARDCORE("Hardcore", "Leitor ávido e colecionador sério");
        
        private final String displayName;
        private final String description;
        
        ReaderLevel(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }
    
    /**
     * Classificação etária preferida
     */
    public enum AgeRating {
        ALL_AGES("Todas as idades", "Conteúdo para todos"),
        TEEN("Adolescentes", "Conteúdo teen"),
        TEEN_PLUS("Teen+", "Conteúdo teen com temas mais maduros"),
        MATURE("Adulto", "Conteúdo para adultos"),
        NO_PREFERENCE("Sem preferência", "Aceita qualquer classificação");
        
        private final String displayName;
        private final String description;
        
        AgeRating(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }
}
