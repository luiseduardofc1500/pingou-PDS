package com.subscription.framework.examples.comics;

/**
 * Gêneros de histórias em quadrinhos.
 */
public enum ComicGenre {
    SUPERHERO("Superhero", "Super-hero comics"),
    MANGA("Manga", "Japanese comics"),
    HORROR("Horror", "Horror and suspense comics"),
    FANTASY("Fantasy", "Fantasy and adventure comics"),
    SCI_FI("Sci-Fi", "Science fiction comics"),
    COMEDY("Comedy", "Comedy and humor comics"),
    DRAMA("Drama", "Drama and slice of life comics"),
    ACTION("Action", "Action and adventure comics"),
    ROMANCE("Romance", "Romance comics"),
    INDEPENDENT("Independent", "Independent/indie comics"),
    GRAPHIC_NOVEL("Graphic Novel", "Graphic novels"),
    CHILDREN("Children", "Comics for children");
    
    private final String displayName;
    private final String description;
    
    ComicGenre(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
}
