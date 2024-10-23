package com.ms_users.models.entity;

import com.ms_users.models.FreeAreaUser;
import com.ms_users.models.PrivateAreaUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_free_area")
    private FreeAreaUser freeAreaUser;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_private_area")
    private PrivateAreaUser privateAreaUser;

    @Transient
    private FreeArea freeArea;

    @Transient
    private PrivateArea privateArea;

    @Column(name = "age")
    private Long age;

    @Column(name = "age_from")
    private Long ageFrom;

    @Column(name = "age_to")
    private Long ageTo;

    @Size(min = 5, message = "Username should have at least 5 characters")
    @Column(name = "username")
    private String username;

    @Size(min = 10, message = "Sex should have at least 5 characters")
    @NotEmpty(message = "Sex cannot be empty")
    @Column(name = "sex")
    private String sex;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    @Column(name = "email", unique = true)
    private String email;

    @Size(min = 5, message = "Birtdate should have not be empty")
    @Column(unique = true, name = "birthdate")
    private LocalDate birthdate;

    @Size(message = "City should have not be empty")
    @Column(name = "city")
    private String city;

    @Size(message = "Country should have not be empty")
    @Column(name = "country")
    private String country;

    @NotBlank
    @Size(message = "Register Date should have not be empty")
    private LocalDate registerDate;

    @NotBlank
    @Size(message = "Description should have not be empty")
    private String description;

    @NotBlank
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Size(min = 5, message = "Password should have at least 5 characters")
    @Column(name = "password")
    private String password;

}