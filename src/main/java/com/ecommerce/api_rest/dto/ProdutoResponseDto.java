package com.ecommerce.api_rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
public class ProdutoResponseDto {
    private UUID id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private String categoria;

    private Integer quantidadeEstoque;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;
}
