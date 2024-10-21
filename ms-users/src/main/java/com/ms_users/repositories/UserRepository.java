package com.ms_users.repositories;

import com.ms_users.dto.UserDTO;
import com.ms_users.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user  " +
            "FROM User user " +
            "WHERE (:#{#userDTO.username} is null or user.username = :#{#userDTO.username}) " +
            "AND (:#{#userDTO.birthdate} is null or user.birthdate = :#{#userDTO.birthdate}) " +
            "AND (:#{#userDTO.city} is null or user.city = :#{#userDTO.city}) " +
            "AND (:#{#userDTO.country} is null or user.country = :#{#userDTO.country}) " +
            "AND (:#{#userDTO.isEnabled} is null or user.isEnabled = :#{#userDTO.isEnabled})")

    public Page<User> filter(@Param("userDTO") UserDTO userDTO, Pageable pageable);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.isEnabled = false " +
            "WHERE u.id = :id")
    void delete(@Param("id") Long id);

}
