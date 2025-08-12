package configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprivado.apiext.model.entity.City;
import com.iprivado.apiext.repositories.CityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner loadData(CityRepository cityRepository) {
        return args -> {
            if (cityRepository.count() == 0) { // Solo cargar si no hay datos
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<List<City>> typeReference = new TypeReference<>() {
                };
                InputStream inputStream = getClass().getResourceAsStream("/data/cities.json");
                List<City> cities = mapper.readValue(inputStream, typeReference);
                cityRepository.saveAll(cities);
                System.out.println("Cities loaded in Mongo db");
            }
        };
    }
}