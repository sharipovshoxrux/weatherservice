package org.example.weather.repository;

import org.example.weather.domain.City;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends ReactiveCrudRepository<City, Long> {
    // Add any additional query methods if needed
}
