package com.ecommerce.api_rest.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class OrderItemDto {
    @NotNull
    private UUID productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
