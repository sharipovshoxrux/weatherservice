package org.example.weather.service.client;

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
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WeatherDataRepository weatherDataRepository;

    @Autowired
    public ClientServiceImpl(UserRepository userRepository,
                             CityRepository cityRepository,
                             SubscriptionRepository subscriptionRepository,
                             WeatherDataRepository weatherDataRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.weatherDataRepository = weatherDataRepository;
    }

    @Override
    public Mono<User> registerUser(String username, String password) {
        return userRepository.findByUsername(username)
                .flatMap(existingUser -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(userRepository.save(new User(username, password)));
    }

    @Override
    public Flux<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public Mono<Void> subscribeToCity(Long userId, Long cityId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> cityRepository.findById(cityId)
                        .switchIfEmpty(Mono.error(new RuntimeException("City not found")))
                        .flatMap(city -> {
                            Subscription subscription = new Subscription();
                            subscription.setUser(user);
                            subscription.setCity(city);
                            return subscriptionRepository.save(subscription);
                        }))
                .then();
    }

    @Override
    public Flux<WeatherData> getSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId)
                .flatMap(subscription -> weatherDataRepository.findByCityId(subscription.getCity().getId()));
    }
}
