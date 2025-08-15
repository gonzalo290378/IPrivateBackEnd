package com.ms_users.repositories;

import com.ms_users.models.entity.Country;
import com.ms_users.models.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository()
public interface StateRepository extends JpaRepository<State, Integer> {

    Optional<State> findByStateAndCountry(String state, Country country);

}
