package com.ms_users.repositories;

import com.ms_users.dto.FilterDTO;
import com.ms_users.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user  " +
            "FROM User user " +
            "WHERE (:#{#filterDTO.preferenceDTO.sexPreference} is null or user.sex = :#{#filterDTO.preferenceDTO.sexPreference}) " +
            "AND (:#{#filterDTO.preferenceDTO.ageFrom} is null or user.age >= :#{#filterDTO.preferenceDTO.ageFrom}) " +
            "AND (:#{#filterDTO.preferenceDTO.ageTo} is null or user.age <= :#{#filterDTO.preferenceDTO.ageTo}) " +
            "AND (:#{#filterDTO.cityDTO.city} is null or user.city.city = :#{#filterDTO.cityDTO.city}) " +
            "AND (:#{#filterDTO.countryDTO.country} is null or user.country.country = :#{#filterDTO.countryDTO.country}) " +
            "AND (:#{#filterDTO.isEnabled} is null or user.isEnabled = :#{#filterDTO.isEnabled}) " +
            "ORDER BY user.id DESC")
    Page<User> filter(@Param("filterDTO") FilterDTO filterDTO, Pageable pageable);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.isEnabled = false " +
            "WHERE u.id = :id")
    void delete(@Param("id") Long id);

    Page<User> findByIsEnabledTrueOrderByIdDesc(Pageable pageable);

    Optional<User> findByEmail(String email);
}
