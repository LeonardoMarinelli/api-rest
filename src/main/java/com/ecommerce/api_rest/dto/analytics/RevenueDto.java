package com.ecommerce.api_rest.dto.analytics;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RevenueDto {
    private BigDecimal totalRevenue;

    public RevenueDto(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
