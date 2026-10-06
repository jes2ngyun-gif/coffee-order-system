package com.nbcamp.coffeeordersystem.domain.order.dto;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotNull Long userId,
        @NotNull Long menuId
) {
}
