package com.ecommerce.api_rest.service;

import com.ecommerce.api_rest.entity.Product;
import com.ecommerce.api_rest.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(UUID id, Product productUpdated) {
        Product existing = findById(id);
        existing.setName(productUpdated.getName());
        existing.setDescription(productUpdated.getDescription());
        existing.setPrice(productUpdated.getPrice());
        existing.setCategory(productUpdated.getCategory());
        existing.setAmountInStock(productUpdated.getAmountInStock());
        return productRepository.save(existing);
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
