package org.example.weather.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class WeatherDataDTO {
    private String city;
    private double temperature;
}
