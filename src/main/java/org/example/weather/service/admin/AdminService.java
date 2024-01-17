package org.example.weather.service.admin;

import org.example.weather.domain.City;
import org.example.weather.domain.Subscription;
import org.example.weather.domain.User;
import org.example.weather.domain.WeatherData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdminService {
    Flux<User> getUserList();
    Mono<Subscription> getUserDetails(Long userId);
    Mono<User> editUser(Long userId, User updatedUser);
    Flux<City> getCitiesList();
    Mono<City> editCity(Long cityId, City updatedCity);
    Mono<WeatherData> updateCityWeather(Long cityId, WeatherData updatedWeatherData);
}
