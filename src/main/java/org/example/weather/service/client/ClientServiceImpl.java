package org.example.weather.service.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weather.domain.entity.Subscription;
import org.example.weather.repository.CityRepository;
import org.example.weather.repository.SubscriptionRepository;
import org.example.weather.repository.UserRepository;
import org.example.weather.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final SubscriptionRepository subscriptionRepository;


    @Override
    public Mono<Void> subscribeToCity(Long userId, Long cityId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> cityRepository.findById(cityId)
                        .switchIfEmpty(Mono.error(new RuntimeException("City not found")))
                        .flatMap(city -> {
                            Subscription subscription = new Subscription();
                            subscription.setUserId(user.getId());
                            subscription.setCityId(city.getId());
                            return subscriptionRepository.save(subscription);
                        }))
                .then();
    }
}
