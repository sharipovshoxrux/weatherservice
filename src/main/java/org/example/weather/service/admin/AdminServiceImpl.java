package org.example.weather.service.admin;

import org.example.weather.domain.City;
import org.example.weather.domain.Subscription;
import org.example.weather.domain.User;
import org.example.weather.domain.WeatherData;
import org.example.weather.repository.CityRepository;
import org.example.weather.repository.SubscriptionRepository;
import org.example.weather.repository.UserRepository;
import org.example.weather.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WeatherDataRepository weatherDataRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository,
                            CityRepository cityRepository,
                            SubscriptionRepository subscriptionRepository,
                            WeatherDataRepository weatherDataRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.weatherDataRepository = weatherDataRepository;
    }

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
                    // Update other user fields as needed
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
                    // Update other city fields as needed
                    return cityRepository.save(existingCity);
                });
    }

    @Override
    public Mono<WeatherData> updateCityWeather(Long cityId, WeatherData updatedWeatherData) {
        return weatherDataRepository.findById(cityId)
                .flatMap(existingData -> {
                    existingData.setTemperature(updatedWeatherData.getTemperature());
                    // Update other weather data fields as needed
                    return weatherDataRepository.save(existingData);
                });
    }
}
