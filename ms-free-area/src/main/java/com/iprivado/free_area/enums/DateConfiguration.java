package com.iprivado.free_area.enums;

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
