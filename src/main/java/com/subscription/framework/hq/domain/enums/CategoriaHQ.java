package com.subscription.framework.hq.domain.enums;

public enum CategoriaHQ {
    SUPER_HEROI("Super Herói"),
    MANGA("Mangá"),
    INDEPENDENTE("Independente"),
    TERROR("Terror"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    FANTASIA("Fantasia"),
    CRIME("Crime"),
    HUMOR("Humor"),
    AVENTURA("Aventura"),
    BIOGRAFIA("Biografia");

    private final String displayName;

    CategoriaHQ(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
