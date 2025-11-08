package com.ecommerce.api_rest.dto.order;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderRequestDto {
    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    private List<OrderItemDto> items;
}
