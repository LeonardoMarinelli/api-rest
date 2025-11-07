package com.ecommerce.api_rest.controller;

import com.ecommerce.api_rest.dto.product.ProductRequestDto;
import com.ecommerce.api_rest.dto.product.ProductResponseDto;
import com.ecommerce.api_rest.entity.Product;
import com.ecommerce.api_rest.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> listAll() {
        List<Product> products = productService.listAll();
        List<ProductResponseDto> responses = products.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable UUID id) {
        Product product = productService.findById(id);
        ProductResponseDto response = mapToResponseDto(product);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody @Valid ProductRequestDto request) {
        Product entity = mapToEntity(request);
        Product created = productService.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapToResponseDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRequestDto requestDto) {
        Product entityToUpdate = mapToEntity(requestDto);
        Product updatedProduct = productService.update(id, entityToUpdate);
        ProductResponseDto responseDto = mapToResponseDto(updatedProduct);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Product mapToEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setAmountInStock(dto.getAmountInStock());
        return product;
    }

    private ProductResponseDto mapToResponseDto(Product product) {
        ProductResponseDto response = new ProductResponseDto();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory());
        response.setAmountInStock(product.getAmountInStock());
        response.setCreatedIn(product.getCreatedIn());
        response.setUpdatedIn(product.getUpdatedIn());
        return response;
    }
}
