package org.example.weather.service.client;

import org.example.weather.domain.City;
import org.example.weather.domain.User;
import org.example.weather.domain.WeatherData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<User> registerUser(String username, String password);

    Flux<City> getCities();

    Mono<Void> subscribeToCity(Long userId, Long cityId);

    Flux<WeatherData> getSubscriptions(Long userId);
}
