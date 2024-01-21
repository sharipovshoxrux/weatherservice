package org.example.weather.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weather.domain.entity.City;
import org.example.weather.domain.entity.Subscription;
import org.example.weather.domain.entity.User;
import org.example.weather.domain.entity.WeatherData;
import org.example.weather.repository.CityRepository;
import org.example.weather.repository.SubscriptionRepository;
import org.example.weather.repository.UserRepository;
import org.example.weather.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WeatherDataRepository weatherDataRepository;

    @Override
    public Flux<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public Mono<Subscription> getUserDetails(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @Override
    public Mono<User> editUser(Long userId, User updatedUser) {
        return userRepository.findById(userId)
                .flatMap(existingUser -> {
                    existingUser.setUsername(updatedUser.getUsername());
                    existingUser.setPassword(updatedUser.getPassword());
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public Flux<City> getCitiesList() {
        return cityRepository.findAll();
    }

    @Override
    public Mono<City> editCity(Long cityId, City updatedCity) {
        return cityRepository.findById(cityId)
                .flatMap(existingCity -> {
                    existingCity.setName(updatedCity.getName());
                    existingCity.setCountry(updatedCity.getCountry());
                    return cityRepository.save(existingCity);
                });
    }

    @Override
    public Mono<WeatherData> updateCityWeather(Long cityId, WeatherData updatedWeatherData) {
        return weatherDataRepository.findById(cityId)
                .flatMap(existingData -> {
                    existingData.setTemperature(updatedWeatherData.getTemperature());
                    return weatherDataRepository.save(existingData);
                });
    }
}
