package com.ecommerce.api_rest.dto.analytics;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
public class TopUserDto {
    private UUID userId;
    private String username;
    private BigDecimal totalSpent;
    private Long ordersCount;

    public TopUserDto(UUID userId, String username, BigDecimal totalSpent, Long ordersCount) {
        this.userId = userId;
        this.username = username;
        this.totalSpent = totalSpent;
        this.ordersCount = ordersCount;
    }
}
