package com.iprivado.apiext.repositories;

import com.iprivado.apiext.model.entity.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends MongoRepository<Country, String> {
    List<Country> findByNameContainingIgnoreCase(String name);
    Country findByName(String name);

}


