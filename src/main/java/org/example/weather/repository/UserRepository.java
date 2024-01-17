package org.example.weather.repository;

import org.example.weather.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    // Add any additional query methods if needed
    Mono<User> findByUsername(String username);
}
