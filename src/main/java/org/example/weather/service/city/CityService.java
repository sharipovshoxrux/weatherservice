package org.example.weather.service.city;

import org.example.weather.domain.dto.response.city.CityResponseDTO;
import reactor.core.publisher.Flux;

public interface CityService {
    Flux<CityResponseDTO> getCitiesList();
}
