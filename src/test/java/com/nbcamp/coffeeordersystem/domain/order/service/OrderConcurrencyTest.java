package com.nbcamp.coffeeordersystem.domain.order.service;

import com.nbcamp.coffeeordersystem.common.exception.BusinessException;
import com.nbcamp.coffeeordersystem.common.exception.ErrorCode;
import com.nbcamp.coffeeordersystem.domain.menu.entity.Menu;
import com.nbcamp.coffeeordersystem.domain.menu.repository.MenuRepository;
import com.nbcamp.coffeeordersystem.domain.order.dto.CreateOrderRequest;
import com.nbcamp.coffeeordersystem.domain.order.repository.OrderRepository;
import com.nbcamp.coffeeordersystem.domain.user.entity.User;
import com.nbcamp.coffeeordersystem.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OrderConcurrencyTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Long userId;
    private Long menuId;

    @BeforeEach
    void setUp() {
        // 이전 테스트가 남긴 데이터를 지운다 (외래 키 때문에 orders를 먼저 지운다)
        orderRepository.deleteAll();
        userRepository.deleteAll();
        menuRepository.deleteAll();

        // 잔액 10,000P인 테스트 사용자를 저장한다
        User user = new User("동시주문테스트");
        user.chargePoint(10000L);
        userId = userRepository.save(user).getId();

        // 4,500P짜리 메뉴를 저장한다
        Menu menu = menuRepository.save(new Menu("아메리카노", 4500L));
        menuId = menu.getId();
    }

    @Test
    @DisplayName("잔액 10,000P인 사용자가 4,500P 메뉴를 동시에 10건 주문하면 2건만 성공하고 잔액은 1,000P가 된다")
    void createOrder_concurrently() throws InterruptedException {
        int threadCount = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger insufficientPointCount = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.createOrder(new CreateOrderRequest(userId, menuId));
                    successCount.incrementAndGet();
                } catch (BusinessException exception) {
                    // 포인트 부족으로 거절된 주문만 센다
                    if (exception.getErrorCode() == ErrorCode.INSUFFICIENT_POINT) {
                        insufficientPointCount.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        // 10건이 모두 끝날 때까지 기다린다
        latch.await();
        executorService.shutdown();

        User user = userRepository.findById(userId).orElseThrow();

        assertThat(successCount.get()).isEqualTo(2);
        assertThat(insufficientPointCount.get()).isEqualTo(8);
        assertThat(user.getPointBalance()).isEqualTo(1000L);
        assertThat(orderRepository.count()).isEqualTo(2);
    }
}