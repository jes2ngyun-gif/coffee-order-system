package com.nbcamp.coffeeordersystem.domain.user.dto;

import com.nbcamp.coffeeordersystem.domain.user.entity.User;

public record ChargePointResponse(Long userId, Long chargedAmount, Long pointBalance) {

    public static ChargePointResponse of(User user, Long chargedAmount) {
        return new ChargePointResponse(user.getId(), chargedAmount, user.getPointBalance());
    }
}
