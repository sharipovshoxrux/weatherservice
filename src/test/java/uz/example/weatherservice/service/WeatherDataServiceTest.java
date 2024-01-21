package uz.example.weatherservice.service;

import org.example.weather.domain.dto.response.weatherdata.WeatherDataResponseDTO;
import org.example.weather.domain.entity.City;
import org.example.weather.domain.entity.Subscription;
import org.example.weather.domain.entity.WeatherData;
import org.example.weather.repository.CityRepository;
import org.example.weather.repository.SubscriptionRepository;
import org.example.weather.repository.WeatherDataRepository;
import org.example.weather.service.weatherdata.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    void getWeatherForSubscribedCities() {

        Subscription subscription = new Subscription(1L, 1L, 1L);
        City city = new City(1L, "TestCity", "TestCountry");
        WeatherData weatherData = new WeatherData(1L, 1L, 25.0);


        when(subscriptionRepository.findByUserId(anyLong())).thenReturn(Mono.just(subscription));
        when(cityRepository.findById(anyLong())).thenReturn(Mono.just(city));
        when(weatherDataRepository.findByCityId(anyLong())).thenReturn(Flux.just(weatherData));

        Mono<WeatherDataResponseDTO> result = weatherService.getWeatherForSubscribedCities(1L);

        StepVerifier.create(result)
                .expectNextMatches(responseDTO ->
                        responseDTO.getCity().equals(city.getName()) &&
                                responseDTO.getTemperature() == weatherData.getTemperature())
                .expectComplete()
                .verify();
    }
}
