package com.nbcamp.coffeeordersystem.domain.menu.dto;

import com.nbcamp.coffeeordersystem.domain.order.dto.PopularMenuResult;

public record PopularMenuResponse(Integer rank, Long menuId, String name, Long orderCount) {

    public static PopularMenuResponse of(int rank, PopularMenuResult result) {
        return new PopularMenuResponse(rank, result.menuId(), result.name(), result.orderCount());
    }
}