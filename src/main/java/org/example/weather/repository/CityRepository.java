package org.example.weather.repository;

import org.example.weather.domain.entity.City;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CityRepository extends ReactiveCrudRepository<City, Long> {

    Mono<City> findById(Long cityId);
}
