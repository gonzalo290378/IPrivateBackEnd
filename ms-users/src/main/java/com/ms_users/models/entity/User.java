package com.ms_users.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
    private List<Role> roles = new ArrayList<>();

    @Size(min = 5, message = "Username should have at least 5 characters")
    @Size(max = 15, message = "Username should not have more than 15 characters")
    @NotEmpty(message = "Username can not be empty")
    private String username;

    @Min(value = 18, message = "Age from must be more than 18")
    @Max(value = 90, message = "Age to must be less than 90")
    @Column(name = "age")
    private Long age;

    @Pattern(regexp = "^(F|M|T|NB)$", message = "Sex must be 'F', 'M', 'T' or 'NB'")
    @NotEmpty(message = "Sex preference must be 'F', 'M', 'T' or 'NB' ")
    @Column(name = "sex")
    private String sex;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    @Column(name = "email", unique = true)
    private String email;

    @Past
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(unique = true, name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "register_date", unique = true)
    @Temporal(TemporalType.DATE)
    private LocalDate registerDate;

    @Size(min = 4)
    @Size(max = 140)
    @NotEmpty(message = "Description can not be empty")
    @Column(name = "description", unique = true)
    private String description;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Size(min = 6)
    @Size(max = 256)
    @NotEmpty(message = "Password can not be empty")
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_state")
    private State state;

    @Transient
    private Boolean admin = false;

}