package com.subscription.framework.examples.jersey;

/**
 * Tamanhos disponíveis para camisas de futebol.
 */
public enum JerseySize {
    PP("PP", "Extra Extra Small"),
    P("P", "Extra Small"),
    M("M", "Medium"),
    G("G", "Large"),
    GG("GG", "Extra Large"),
    XGG("XGG", "Extra Extra Large"),
    SPECIAL("Special", "Custom/Special Size");
    
    private final String code;
    private final String description;
    
    JerseySize(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getDisplayName() {
        return code;
    }
}
