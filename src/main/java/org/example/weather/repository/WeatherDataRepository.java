package org.example.weather.repository;

import org.example.weather.domain.entity.WeatherData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WeatherDataRepository extends ReactiveCrudRepository<WeatherData, Long> {
    Flux<WeatherData> findByCityId(Long id);
}
