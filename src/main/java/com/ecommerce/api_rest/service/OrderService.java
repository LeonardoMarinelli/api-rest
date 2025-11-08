package com.ecommerce.api_rest.service;

import com.ecommerce.api_rest.dto.order.OrderItemDto;
import com.ecommerce.api_rest.dto.order.OrderRequestDto;
import com.ecommerce.api_rest.dto.order.OrderResponseDto;
import com.ecommerce.api_rest.entity.Order;
import com.ecommerce.api_rest.entity.OrderItem;
import com.ecommerce.api_rest.entity.Product;
import com.ecommerce.api_rest.entity.User;
import com.ecommerce.api_rest.entity.enums.OrderStatus;
import com.ecommerce.api_rest.exception.ResourceNotFoundException;
import com.ecommerce.api_rest.repository.OrderRepository;
import com.ecommerce.api_rest.repository.ProductRepository;
import com.ecommerce.api_rest.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                         ProductRepository productRepository,
                         UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + username));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDENTE);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> itens = new ArrayList<>();

        for (OrderItemDto itemDto : request.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + itemDto.getProductId()));

            if (itemDto.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero para produto: " + product.getId());
            }

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            total = total.add(itemTotal);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setUnitPrice(product.getPrice());
            itens.add(item);
        }

        order.setItems(itens);
        order.setTotal(total);

        Order savedOrder = orderRepository.save(order);

        return mapToResponseDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto payOrder(UUID orderId, String username) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: " + orderId));

        if (!order.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Usuário não autorizado para pagar este pedido");
        }

        if (order.getStatus() != OrderStatus.PENDENTE) {
            throw new IllegalArgumentException("Pedido não está em estado PENDENTE");
        }

        for (OrderItem item : order.getItems()) {
            Product produto = item.getProduct();
            if (produto.getAmountInStock() < item.getQuantity()) {
                order.setStatus(OrderStatus.CANCELADO);
                order.setCancelledIn(java.time.LocalDateTime.now());
                orderRepository.save(order);
                throw new IllegalStateException("Estoque insuficiente para produto: " + produto.getId());
            }
        }

        for (OrderItem item : order.getItems()) {
            Product produto = item.getProduct();
            produto.setAmountInStock(produto.getAmountInStock() - item.getQuantity());
            productRepository.save(produto);
        }

        order.setStatus(OrderStatus.PAGO);
        order.setPaidIn(java.time.LocalDateTime.now());
        Order paidOrder = orderRepository.save(order);

        return mapToResponseDto(paidOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> listOrders(String username) {
        List<Order> orders = orderRepository.findByUser_Username(username);
        return orders.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> listOrdersByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + userId));

        List<Order> orders = orderRepository.findByUser_Id(userId);
        return orders.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto mapToResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedIn(order.getCreatedIn());
        dto.setPaidIn(order.getPaidIn());
        dto.setCancelledIn(order.getCancelledIn());

        List<OrderResponseDto.ItemInfo> itemsInfo = order.getItems().stream()
                .map(item -> {
                    OrderResponseDto.ItemInfo info = new OrderResponseDto.ItemInfo();
                    info.setProductId(item.getProduct().getId());
                    info.setProductName(item.getProduct().getName());
                    info.setUnitPrice(item.getUnitPrice());
                    info.setQuantity(item.getQuantity());
                    return info;
                })
                .collect(Collectors.toList());
        dto.setItems(itemsInfo);

        return dto;
    }
}
