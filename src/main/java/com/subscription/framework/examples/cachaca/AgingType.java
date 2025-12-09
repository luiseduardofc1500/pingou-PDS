package com.subscription.framework.examples.cachaca;

/**
 * Tipos de envelhecimento de cachaça.
 */
public enum AgingType {
    NONE("None", "No aging"),
    OAK("Oak", "Aged in oak barrels"),
    AMBURANA("Amburana", "Aged in amburana wood barrels"),
    JEQUITIBA("Jequitibá", "Aged in jequitibá wood barrels"),
    BALSAMO("Bálsamo", "Aged in bálsamo wood barrels"),
    FREIJO("Freijó", "Aged in freijó wood barrels"),
    MIXED("Mixed", "Aged in multiple wood types");
    
    private final String displayName;
    private final String description;
    
    AgingType(String displayName, String description) {
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
