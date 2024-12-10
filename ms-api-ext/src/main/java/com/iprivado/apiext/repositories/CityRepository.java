package com.iprivado.apiext.repositories;

import com.iprivado.apiext.model.entity.City;
import com.iprivado.apiext.model.entity.Country;
import com.iprivado.apiext.model.entity.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends MongoRepository<City, String> {
    List<Country> findByNameContainingIgnoreCase(String name);

    @Query("{ 'id_state': ?0 }")
    List<City> findByIdState(int stateId);
}