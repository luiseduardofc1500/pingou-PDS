package com.subscription.framework.core.domain.enums;


public enum DeliveryFrequency {
    WEEKLY("Weekly", 7),
    BIWEEKLY("Biweekly", 14),
    MONTHLY("Monthly", 30),
    BIMONTHLY("Bimonthly", 60),
    QUARTERLY("Quarterly", 90),
    SEMIANNUAL("Semiannual", 180),
    ANNUAL("Annual", 365);

    private final String description;
    private final int daysInterval;

    DeliveryFrequency(String description, int daysInterval) {
        this.description = description;
        this.daysInterval = daysInterval;
    }

    public String getDescription() {
        return description;
    }

    public int getDaysInterval() {
        return daysInterval;
    }


    public int getDeliveriesPerYear() {
        return 365 / daysInterval;
    }
}
