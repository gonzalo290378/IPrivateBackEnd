package com.ms_users.enums;

import java.time.LocalDate;

public enum DateConfiguration {
    TODAY(LocalDate.now());

    private final LocalDate value;

    DateConfiguration(LocalDate value) {
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }
}
