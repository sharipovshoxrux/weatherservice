package org.example.weather.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class CityDTO {
    private Long id;
    private String name;
    private String country;
}
