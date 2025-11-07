package com.ecommerce.api_rest.dto.product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ProductRequestDto {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private String description;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    private BigDecimal price;

    @NotBlank(message = "Categoria é obrigatória")
    private String category;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Min(value = 0, message = "Quantidade em estoque não pode ser negativa")
    private Integer amountInStock;
}
