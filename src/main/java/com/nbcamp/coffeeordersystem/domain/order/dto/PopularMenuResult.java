package com.nbcamp.coffeeordersystem.domain.order.dto;

// 인기 메뉴 집계 쿼리의 결과 한 줄
public record PopularMenuResult(Long menuId, String name, Long orderCount) {
}
