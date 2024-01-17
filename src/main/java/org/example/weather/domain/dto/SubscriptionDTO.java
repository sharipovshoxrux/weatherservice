package org.example.weather.domain.dto;

import lombok.Data;

@Data
public class SubscriptionDTO {
    private Long userId;
    private Long cityId;
}
