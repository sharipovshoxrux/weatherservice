package org.example.weather.domain.dto.request.subscription;

import lombok.Data;

@Data
public class SubscriptionRequestDTO {
    private Long userId;
    private Long cityId;
}
