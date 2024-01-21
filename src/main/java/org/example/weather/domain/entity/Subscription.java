package org.example.weather.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("subscriptions")
@AllArgsConstructor
public class Subscription {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("city_id")
    private Long cityId;

    public Subscription() {

    }

    // Other subscription-related fields and methods
}