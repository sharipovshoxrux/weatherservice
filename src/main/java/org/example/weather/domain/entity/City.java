package org.example.weather.domain.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("cities")
public class City {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String country;

    @Column
    private boolean visible;

    public City(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    // Other city-related fields and methods
}
