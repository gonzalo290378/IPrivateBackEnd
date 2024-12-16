package com.iprivado.apiext.repositories;

import com.iprivado.apiext.model.entity.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StateRepository extends MongoRepository<State, String> {

    @Query("{ 'id_country': ?0 }")
    List<State> findByIdCountry(int idCountry);

    @Query("{ 'id_country': ?1, 'name': ?0 }")
    State findByNameAndIdCountry(String name, int idCountry);

}
