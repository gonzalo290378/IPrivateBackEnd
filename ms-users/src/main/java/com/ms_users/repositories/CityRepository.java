package com.ms_users.repositories;

import com.ms_users.models.entity.City;
import com.ms_users.models.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository()
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByCityAndState(String city, State state);
}
