package com.ms_users.repositories;

import com.ms_users.dto.UserDTO;
import com.ms_users.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user  " +
            "FROM User user " +
            "WHERE (:#{#userListDTO.sex} is null or user.sex = :#{#userListDTO.sex}) " +
            "AND (:#{#userListDTO.ageFrom} is null or user.age >= :#{#userListDTO.ageFrom}) " +
            "AND (:#{#userListDTO.ageTo} is null or user.age <= :#{#userListDTO.ageTo}) " +
            "AND (:#{#userListDTO.city} is null or user.city = :#{#userListDTO.city}) " +
            "AND (:#{#userListDTO.country} is null or user.country = :#{#userListDTO.country}) " +
            "AND (:#{#userListDTO.country} is null or user.country = :#{#userListDTO.country}) " +
            "AND (:#{#userListDTO.isEnabled} is null or user.isEnabled = :#{#userListDTO.isEnabled})")
    Page<User> filter(@Param("userListDTO") UserDTO userListDTO, Pageable pageable);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.isEnabled = false " +
            "WHERE u.id = :id")
    void delete(@Param("id") Long id);

}
