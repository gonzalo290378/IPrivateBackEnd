package com.ms_users.exceptions;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

    private LocalDate timeStamp;
    private String message;
    private String detail;

}
