package com.ecommerce.api_rest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer amountInStock;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdIn;

    @Column(nullable = false)
    private LocalDateTime updatedIn;

    public Product() {

    }

    @PrePersist
    protected void onCreate() {
        createdIn = LocalDateTime.now();
        updatedIn = createdIn;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedIn = LocalDateTime.now();
    }
}
