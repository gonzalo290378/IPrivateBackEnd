package com.ms_users.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column(name = "id_free_area")
    private Long idFreeArea;

    @Column(name = "id_private_area")
    private Long idPrivateArea;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_preference")
    private Preference preference;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_country")
    private Country country;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_city")
    private City city;

    @Size(min = 5, message = "Username should have at least 5 characters")
    @NotEmpty(message = "Username cannot be empty")
    @Column(name = "username")
    private String username;

    @Column(name = "age")
    @Min(18)
    private Long age;

    @NotEmpty(message = "Sex cannot be empty")
    @Column(name = "sex")
    private String sex;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    @Column(name = "email", unique = true)
    private String email;

    @Past
    @Temporal(TemporalType.DATE)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(unique = true, name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "register_date", unique = true)
    @Temporal(TemporalType.DATE)
    private LocalDate registerDate;

    @NotBlank
    @Size(max = 140, message = "The text must not exceed 140 characters")
    @Column(name = "description", unique = true)
    private String description;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Size(min = 5, message = "Password should have at least 5 characters")
    @Column(name = "password")
    private String password;

}