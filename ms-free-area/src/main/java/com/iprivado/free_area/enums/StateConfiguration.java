package com.iprivado.free_area.enums;

public enum StateConfiguration {

    ENABLED(true),
    DISABLED(false);

    private final Boolean value;

    StateConfiguration(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}
