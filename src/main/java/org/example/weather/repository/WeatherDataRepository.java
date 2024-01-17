package org.example.weather.repository;

import org.example.weather.domain.WeatherData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends ReactiveCrudRepository<WeatherData, Long> {
    // Add any additional query methods if needed
}
