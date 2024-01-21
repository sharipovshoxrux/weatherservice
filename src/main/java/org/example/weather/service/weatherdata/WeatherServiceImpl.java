package org.example.weather.service.weatherdata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weather.domain.dto.response.weatherdata.WeatherDataResponseDTO;
import org.example.weather.domain.entity.WeatherData;
import org.example.weather.repository.CityRepository;
import org.example.weather.repository.SubscriptionRepository;
import org.example.weather.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final SubscriptionRepository subscriptionRepository;
    private final CityRepository cityRepository;
    private final WeatherDataRepository weatherDataRepository;

    @Override
    public Mono<WeatherDataResponseDTO> getWeatherForSubscribedCities(Long userId) {
        return subscriptionRepository.findByUserId(userId)
                .flatMap(subscription -> cityRepository.findById(subscription.getCityId()))
                .flatMap(city -> weatherDataRepository.findByCityId(city.getId())
                        .collectList()
                        .map(weatherDataList -> mapToWeatherDataResponseDTO(city.getName(), weatherDataList)))
                .flatMap(Mono::justOrEmpty);
    }

    private WeatherDataResponseDTO mapToWeatherDataResponseDTO(String cityName, List<WeatherData> weatherDataList) {
        WeatherDataResponseDTO responseDTO = new WeatherDataResponseDTO();
        responseDTO.setCity(cityName);
        responseDTO.setTemperature(calculateAverageTemperature(weatherDataList));
        return responseDTO;
    }

    private double calculateAverageTemperature(List<WeatherData> weatherDataList) {
        if (weatherDataList.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (WeatherData weatherData : weatherDataList) {
            sum += weatherData.getTemperature();
        }
        return sum / weatherDataList.size();
    }
}
