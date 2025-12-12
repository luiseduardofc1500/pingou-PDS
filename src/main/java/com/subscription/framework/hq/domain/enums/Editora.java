package com.subscription.framework.hq.domain.enums;

public enum Editora {
    MARVEL("Marvel Comics"),
    DC("DC Comics"),
    IMAGE("Image Comics"),
    DARK_HORSE("Dark Horse Comics"),
    IDW("IDW Publishing"),
    PANINI("Panini Comics"),
    VERTIGO("Vertigo"),
    OUTROS("Outros");

    private final String displayName;

    Editora(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
