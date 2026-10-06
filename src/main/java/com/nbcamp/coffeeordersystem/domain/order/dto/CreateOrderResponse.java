package com.nbcamp.coffeeordersystem.domain.order.dto;

import com.nbcamp.coffeeordersystem.domain.order.entity.Order;
import com.nbcamp.coffeeordersystem.domain.user.entity.User;

import java.time.LocalDateTime;

public record CreateOrderResponse(
        Long orderId,
        Long userId,
        Long menuId,
        String menuName,
        Long paymentAmount,
        Long pointBalance,
        LocalDateTime orderedAt
) {

    public static CreateOrderResponse of(Order order, User user) {
        return new CreateOrderResponse(
                order.getId(),
                user.getId(),
                order.getMenu().getId(),
                order.getMenuName(),
                order.getPaymentAmount(),
                user.getPointBalance(),
                order.getOrderedAt()
        );
    }
}
