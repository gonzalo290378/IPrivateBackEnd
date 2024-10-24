package com.ms_users.enums;

public enum AgeConfiguration {
    ADULT(18L),
    SENIOR(90L);

    private final Long value;

    AgeConfiguration(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
