package org.example.weather.controller.client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.weather.domain.dto.request.auth.LoginRequestDTO;
import org.example.weather.domain.dto.request.subscription.SubscriptionRequestDTO;
import org.example.weather.domain.dto.response.auth.AuthenticationResponseDTO;
import org.example.weather.domain.dto.response.city.CityResponseDTO;
import org.example.weather.domain.dto.response.weatherdata.WeatherDataResponseDTO;
import org.example.weather.service.auth.AuthenticationService;
import org.example.weather.service.city.CityService;
import org.example.weather.service.client.ClientService;
import org.example.weather.service.weatherdata.WeatherService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final AuthenticationService authService;
    private final CityService cityService;
    private final WeatherService weatherService;

    @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Receiving a token",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AuthenticationResponseDTO.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404",
                            description = "User not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    public ResponseEntity<?> register(@RequestBody @Valid LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }

    @GetMapping(path = "/cities-list", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "List of cities",
            security = {@SecurityRequirement(name = "bearer-key")},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    public Flux<CityResponseDTO> getCitiesList() {
        return cityService.getCitiesList();
    }

    @PostMapping(path = "/subscribe-to-city", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "City subscription", security = {@SecurityRequirement(name = "bearer-key")})
    public Mono<ResponseEntity<String>> subscribeToCity(@RequestBody SubscriptionRequestDTO subscriptionDTO) {
        return clientService.subscribeToCity(subscriptionDTO.getUserId(), subscriptionDTO.getCityId())
                .map(v -> ResponseEntity.ok("Subscribed to city successfully"))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(error.getMessage())));
    }

    @GetMapping(path = "/subscribed-cities/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get the weather data for subscribed cities",
            security = {@SecurityRequirement(name = "bearer-key")},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    public Mono<WeatherDataResponseDTO> getWeatherForSubscribedCities(@PathVariable Long userId) {
        return weatherService.getWeatherForSubscribedCities(userId);
    }
}
