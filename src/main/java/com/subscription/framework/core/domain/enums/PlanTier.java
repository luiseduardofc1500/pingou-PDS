package com.subscription.framework.core.domain.enums;

public enum PlanTier {
    BASIC("Basic", 1),
    STANDARD("Standard", 2),
    PREMIUM("Premium", 3),
    ENTERPRISE("Enterprise", 4);

    private final String displayName;
    private final int level;

    PlanTier(String displayName, int level) {
        this.displayName = displayName;
        this.level = level;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Verifica se este tier é igual ou superior a outro.
     */
    public boolean isAtLeast(PlanTier other) {
        return this.level >= other.level;
    }

    /**
     * Verifica se este tier é superior a outro.
     */
    public boolean isHigherThan(PlanTier other) {
        return this.level > other.level;
    }
}
