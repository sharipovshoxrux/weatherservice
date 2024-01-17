package org.example.weather.repository;

import org.example.weather.domain.Subscription;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, Long> {
    Mono<Subscription> findByUserId(Long userId);
}
