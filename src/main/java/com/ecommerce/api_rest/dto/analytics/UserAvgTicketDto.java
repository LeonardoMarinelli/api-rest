package com.ecommerce.api_rest.dto.analytics;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class UserAvgTicketDto {
    private UUID userId;
    private String username;
    private Double avgTicket;

    public UserAvgTicketDto(UUID userId, String username, Double avgTicket) {
        this.userId = userId;
        this.username = username;
        this.avgTicket = avgTicket;
    }
}
