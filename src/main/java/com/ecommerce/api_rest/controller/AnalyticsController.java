package com.ecommerce.api_rest.controller;

import com.ecommerce.api_rest.dto.analytics.RevenueDto;
import com.ecommerce.api_rest.dto.analytics.TopUserDto;
import com.ecommerce.api_rest.dto.analytics.UserAvgTicketDto;
import com.ecommerce.api_rest.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final OrderRepository orderRepository;

    public AnalyticsController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/top-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TopUserDto>> getTopUsers() {
        List<TopUserDto> top = orderRepository.findTopUsers(PageRequest.of(0,5));
        return ResponseEntity.ok(top);
    }

    @GetMapping("/average-ticket-by-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAvgTicketDto>> getAvgTicketByUser() {
        List<UserAvgTicketDto> list = orderRepository.findAvgTicketByUser();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/revenue/current-month")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RevenueDto> getRevenueCurrentMonth() {
        BigDecimal revenue = orderRepository.findTotalRevenueCurrentMonth();
        return ResponseEntity.ok(new RevenueDto(revenue));
    }
}
