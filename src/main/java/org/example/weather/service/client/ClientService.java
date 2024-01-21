package org.example.weather.service.client;

import org.example.weather.domain.entity.City;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<Void> subscribeToCity(Long userId, Long cityId);
}
