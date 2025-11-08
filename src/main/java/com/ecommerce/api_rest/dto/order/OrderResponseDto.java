package com.ecommerce.api_rest.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class OrderResponseDto {
    private UUID id;
    private UUID userId;
    private BigDecimal total;
    private String status;
    private LocalDateTime createdIn;
    private LocalDateTime paidIn;
    private LocalDateTime cancelledIn;
    private List<ItemInfo> items;

    @Getter @Setter
    public static class ItemInfo {
        private UUID productId;
        private String productName;
        private BigDecimal unitPrice;
        private Integer quantity;
    }
}
