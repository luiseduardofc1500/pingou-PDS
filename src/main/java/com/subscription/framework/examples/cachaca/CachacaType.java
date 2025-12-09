package com.subscription.framework.examples.cachaca;

/**
 * Tipos de cachaça.
 */
public enum CachacaType {
    BRANCA("White", "Fresh, unaged cachaça"),
    OURO("Gold", "Lightly aged cachaça"),
    ENVELHECIDA("Aged", "Aged cachaça in wooden barrels"),
    PREMIUM("Premium", "Premium aged cachaça"),
    EXTRA_PREMIUM("Extra Premium", "Extra premium long-aged cachaça");
    
    private final String displayName;
    private final String description;
    
    CachacaType(String displayName, String description) {
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
