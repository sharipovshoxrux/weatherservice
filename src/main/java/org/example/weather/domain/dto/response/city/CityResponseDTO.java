package org.example.weather.domain.dto.response.city;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityResponseDTO {
    private String name;
    private String country;
}
