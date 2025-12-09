package com.subscription.framework.examples.jersey;

/**
 * Tipos de camisa de futebol.
 */
public enum JerseyType {
    HOME("Home", "Main jersey used in home games"),
    AWAY("Away", "Jersey used in away games"),
    THIRD("Third", "Alternative third jersey"),
    FOURTH("Fourth", "Fourth alternative jersey"),
    GOALKEEPER("Goalkeeper", "Goalkeeper jersey"),
    TRAINING("Training", "Training jersey"),
    PRE_MATCH("Pre-Match", "Pre-match warm-up jersey"),
    SPECIAL("Special Edition", "Special edition jersey");
    
    private final String displayName;
    private final String description;
    
    JerseyType(String displayName, String description) {
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
