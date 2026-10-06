package com.nbcamp.coffeeordersystem.domain.order.event;

// 주문 완료 소식에 담는 내용: 데이터 수집 플랫폼으로 전송할 세 값(엔티티 ㄴㄴ. 에러 날 수 있음)
public record OrderCompletedEvent(Long userId, Long menuId, Long paymentAmount) {
}
