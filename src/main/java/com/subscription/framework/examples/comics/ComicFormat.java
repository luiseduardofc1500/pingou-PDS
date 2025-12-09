package com.subscription.framework.examples.comics;

/**
 * Formatos de publicação de HQs.
 */
public enum ComicFormat {
    SINGLE_ISSUE("Single Issue", "Individual comic book issue"),
    TRADE_PAPERBACK("Trade Paperback", "Collection of issues in paperback"),
    HARDCOVER("Hardcover", "Collection of issues in hardcover"),
    OMNIBUS("Omnibus", "Large collection of issues"),
    TANKOBON("Tankōbon", "Japanese manga volume"),
    DIGEST("Digest", "Smaller format digest"),
    DIGITAL("Digital", "Digital only format");
    
    private final String displayName;
    private final String description;
    
    ComicFormat(String displayName, String description) {
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
