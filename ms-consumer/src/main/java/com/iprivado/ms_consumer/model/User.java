package com.iprivado.ms_consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private Long age;

    private String sex;

    private String email;

    private LocalDate birthdate;

    private LocalDate registerDate;

    private String description;

    private Boolean isEnabled;

    private String password;

    private Long idFreeArea;

    private Long idPrivateArea;

    public Long getAge() {
        LocalDate today = LocalDate.now();
        long age = Period.between(birthdate, today).getYears();

        if (birthdate.getDayOfMonth() == today.getDayOfMonth() && birthdate.getMonth() == today.getMonth()) {
            return age + 1;
        }

        return age;
    }

}
