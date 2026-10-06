package com.nbcamp.coffeeordersystem.domain.order.event;

import com.nbcamp.coffeeordersystem.domain.order.sender.DataPlatformSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor

public class OrderEventListener {

    private final DataPlatformSender dataPlatformSender;

    // 주문 트랜잭션이 커밋된 뒤에만 실행됌. 롤백되면 실행되지 않음.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCompleted(OrderCompletedEvent event) {
        try {
            dataPlatformSender.send(event);

        } catch (Exception exception) {
            // 전송에 실패해도 이미 커밋된 주문에는 영향을 주지 않음
            log.error(
                    "[DataPlatform] 주문 내역 전송 실패: userId={}, menuId={}, paymentAmount={}",
                    event.userId(),
                    event.menuId(),
                    event.paymentAmount(),
                    exception
            );
        }
    }
}
