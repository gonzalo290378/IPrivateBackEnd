package com.ms_users.models.entity;

import com.ms_users.models.FreeArea;
import com.ms_users.models.PrivateArea;
import com.ms_users.models.Subscription;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_free_area")
    private FreeAreaUser freeAreaUser;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_private_area")
    private PrivateAreaUser privateAreaUser;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_preference")
    private Preference preference;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_country")
    private Country country;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_city")
    private City city;

    @Transient
    private FreeArea freeArea;

    @Transient
    private PrivateArea privateArea;

    @Size(min = 5, message = "Username should have at least 5 characters")
    @NotEmpty(message = "Username cannot be empty")
    @Column(name = "username")
    private String username;

    @Column(name = "age")
    @Min(18)
    private Long age;

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

    @NotBlank
    @Size(message = "Register Date should have not be empty")
    private LocalDate registerDate;

    @NotBlank
    @Size(message = "Description should have not be empty")
    @Size(max = 140, message = "The text must not exceed 140 characters")
    private String description;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Size(min = 5, message = "Password should have at least 5 characters")
    @Column(name = "password")
    private String password;

}