package com.nbcamp.coffeeordersystem.domain.user.dto;

import jakarta.validation.constraints.NotNull;

public record ChargePointRequest(
        @NotNull Long userId,
        @NotNull Long amount
) {
}