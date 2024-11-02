package com.ms_users.enums;

public enum AreaConfiguration {
    ENABLED(true),
    DISABLED(false);

    private final Boolean value;

    AreaConfiguration(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}
