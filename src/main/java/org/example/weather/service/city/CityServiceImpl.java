package org.example.weather.service.city;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weather.domain.dto.response.city.CityResponseDTO;
import org.example.weather.repository.CityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public Flux<CityResponseDTO> getCitiesList() {
        return cityRepository.findAll()
                .map(city -> CityResponseDTO.builder()
                        .name(city.getName())
                        .country(city.getCountry())
                        .build());
    }
}
