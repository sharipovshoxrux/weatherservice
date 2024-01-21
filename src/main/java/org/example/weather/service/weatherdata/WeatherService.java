package org.example.weather.service.weatherdata;

import org.example.weather.domain.dto.response.weatherdata.WeatherDataResponseDTO;
import reactor.core.publisher.Mono;

public interface WeatherService {
    Mono<WeatherDataResponseDTO> getWeatherForSubscribedCities(Long userId);
}
