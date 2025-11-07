package com.ecommerce.api_rest.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
public class ProductResponseDto {
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private String category;

    private Integer amountInStock;

    private LocalDateTime createdIn;

    private LocalDateTime updatedIn;
}
