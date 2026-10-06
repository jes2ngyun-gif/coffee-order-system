package com.nbcamp.coffeeordersystem.domain.order.sender;

import com.nbcamp.coffeeordersystem.domain.order.event.OrderCompletedEvent;

// 주문 내역을 데이터 수집 플랫폼으로 전송하는 창구
public interface DataPlatformSender {

    void send(OrderCompletedEvent event);
}
