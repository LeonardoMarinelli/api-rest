package com.ecommerce.api_rest.controller;

import com.ecommerce.api_rest.dto.order.OrderRequestDto;
import com.ecommerce.api_rest.dto.order.OrderResponseDto;
import com.ecommerce.api_rest.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody @Valid OrderRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        OrderResponseDto response = orderService.createOrder(request, username);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/{orderId}/pay")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDto> payOrder(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        OrderResponseDto response = orderService.payOrder(orderId, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderResponseDto>> listOrders(
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        List<OrderResponseDto> list = orderService.listOrders(username);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> listOrdersByUser(
            @PathVariable UUID userId) {
        List<OrderResponseDto> list = orderService.listOrdersByUser(userId);
        return ResponseEntity.ok(list);
    }
}
