package com.ecommerce.api_rest.repository;

import com.ecommerce.api_rest.dto.analytics.TopUserDto;
import com.ecommerce.api_rest.dto.analytics.UserAvgTicketDto;
import com.ecommerce.api_rest.entity.Order;
import com.ecommerce.api_rest.entity.enums.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser_Username(String username);
    List<Order> findByUser_Id(UUID userId);

    @Query(value = "SELECT o.user.id AS userId, o.user.username AS username, SUM(o.total) AS totalSpent, COUNT(o.id) AS ordersCount " +
            "FROM Order o WHERE o.status = com.ecommerce.api_rest.entity.enums.OrderStatus.PAGO " +
            "GROUP BY o.user.id, o.user.username " +
            "ORDER BY SUM(o.total) DESC")
    List<TopUserDto> findTopUsers(Pageable pageable);

    @Query("SELECT new com.ecommerce.api_rest.dto.analytics.UserAvgTicketDto(u.id, u.username, AVG(o.total)) " +
            "FROM Order o JOIN o.user u " +
            "WHERE o.status = com.ecommerce.api_rest.entity.enums.OrderStatus.PAGO " +
            "GROUP BY u.id, u.username")
    List<UserAvgTicketDto> findAvgTicketByUser();

    @Query(value = "SELECT SUM(o.total) FROM Order o WHERE o.status = com.ecommerce.api_rest.entity.enums.OrderStatus.PAGO " +
            "AND FUNCTION('DATE_FORMAT', o.paidIn, '%Y-%m') = FUNCTION('DATE_FORMAT', CURRENT_DATE, '%Y-%m')")
    BigDecimal findTotalRevenueCurrentMonth();
}
