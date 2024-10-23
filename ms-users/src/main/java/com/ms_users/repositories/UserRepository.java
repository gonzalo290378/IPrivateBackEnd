package com.ms_users.repositories;

import com.ms_users.dto.FilterDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user  " +
            "FROM User user " +
            "WHERE (:#{#filterDTO.sex} is null or user.sex = :#{#filterDTO.sex}) " +
            "AND (:#{#filterDTO.ageFrom} is null or user.age >= :#{#filterDTO.ageFrom}) " +
            "AND (:#{#filterDTO.ageTo} is null or user.age <= :#{#filterDTO.ageTo}) " +
            "AND (:#{#filterDTO.city} is null or user.city = :#{#filterDTO.city}) " +
            "AND (:#{#filterDTO.country} is null or user.country = :#{#filterDTO.country}) " +
            "AND (:#{#filterDTO.country} is null or user.country = :#{#filterDTO.country}) " +
            "AND (:#{#filterDTO.isEnabled} is null or user.isEnabled = :#{#filterDTO.isEnabled})")

    Page<User> filter(@Param("filterDTO") FilterDTO filterDTO, Pageable pageable);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.isEnabled = false " +
            "WHERE u.id = :id")
    void delete(@Param("id") Long id);

}
