package com.ecommerce.api_rest.repository;

import com.ecommerce.api_rest.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser_Username(String username);
    List<Order> findByUser_Id(UUID userId);
}
