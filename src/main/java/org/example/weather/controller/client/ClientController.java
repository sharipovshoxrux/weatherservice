package org.example.weather.controller.client;

import org.example.weather.domain.dto.CityDTO;
import org.example.weather.domain.dto.SubscriptionDTO;
import org.example.weather.domain.dto.WeatherDataDTO;
import org.example.weather.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestParam String username, @RequestParam String password) {
        return clientService.registerUser(username, password)
                .map(user -> ResponseEntity.ok("User registered successfully"))
                .defaultIfEmpty(ResponseEntity.status(409).body("User already exists"));
    }

    @GetMapping("/cities")
    public Flux<CityDTO> getCities() {
        return clientService.getCities()
                .map(city -> new CityDTO(city.getId(), city.getName(), city.getCountry()));
    }

    @PostMapping("/subscribe")
    public Mono<ResponseEntity<String>> subscribeToCity(@RequestBody SubscriptionDTO subscriptionDTO) {
        return clientService.subscribeToCity(subscriptionDTO.getUserId(), subscriptionDTO.getCityId())
                .map(v -> ResponseEntity.ok("Subscribed to city successfully"))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(error.getMessage())));
    }

    @GetMapping("/subscriptions")
    public Flux<WeatherDataDTO> getSubscriptions(@RequestParam Long userId) {
        return clientService.getSubscriptions(userId)
                .map(weatherData -> new WeatherDataDTO());
    }
}
