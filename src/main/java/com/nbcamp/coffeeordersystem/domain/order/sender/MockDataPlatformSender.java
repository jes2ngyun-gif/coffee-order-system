package com.nbcamp.coffeeordersystem.domain.order.sender;

import com.nbcamp.coffeeordersystem.domain.order.event.OrderCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// 실제 플랫폼 대신 로그로 전송을 대신하는 Mock 구현체
@Slf4j
@Component

public class MockDataPlatformSender implements DataPlatformSender {

    @Override
    public void send(OrderCompletedEvent event) {
        log.info("[DataPlatform] 주문 내역 전송: userId={}, menuId={}, paymentAmount={}",
                event.userId(), event.menuId(), event.paymentAmount());
    }
}
