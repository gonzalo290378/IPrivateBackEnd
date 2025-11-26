package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms_users.models.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class
UserProfileDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    private List<Role> roles = new ArrayList<>();

    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    @NotEmpty(message = "Username cannot be empty")
    @JsonProperty("username")
    private String username;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    @JsonProperty("email")
    private String email;

    @Size(min = 5, max = 14, message = "Password debe tener entre 5 y 14 caracteres")
    @NotEmpty(message = "Password can not be empty")
    @JsonProperty("password")
    private String password;

}
