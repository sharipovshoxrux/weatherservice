package org.example.weather.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("weather_data")
@AllArgsConstructor
public class WeatherData {

    @Id
    private Long id;

    @Column("city_id")
    private Long cityId;

    @Column
    private double temperature;

    // Other weather-related fields and methods
}
