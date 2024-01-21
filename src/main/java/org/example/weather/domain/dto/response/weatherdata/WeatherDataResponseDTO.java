package org.example.weather.domain.dto.response.weatherdata;

import lombok.Data;

@Data
public class WeatherDataResponseDTO {
    private String city;
    private double temperature;
}
