package com.ms_users.enums;

public enum UserEnabledConfiguration {
    IS_ENABLED(true);

    private final Boolean value;

    UserEnabledConfiguration(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}
