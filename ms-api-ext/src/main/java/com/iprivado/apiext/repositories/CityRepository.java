package com.iprivado.apiext.repositories;

import com.iprivado.apiext.model.entity.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends MongoRepository<City, String> {

    @Query("{ 'id_state': ?0 }")
    List<City> findByIdState(int idState);
}