package com.subscription.framework.hq.domain.enums;

public enum TipoHQ {
    CLASSICA("Clássica", 150), // HQs publicadas há mais de 10 anos
    MODERNA("Moderna", 100);   // HQs recentes

    private final String displayName;
    private final int pontosBase;

    TipoHQ(String displayName, int pontosBase) {
        this.displayName = displayName;
        this.pontosBase = pontosBase;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPontosBase() {
        return pontosBase;
    }
}
