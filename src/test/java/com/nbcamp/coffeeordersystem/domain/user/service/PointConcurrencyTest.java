package com.nbcamp.coffeeordersystem.domain.user.service;

import com.nbcamp.coffeeordersystem.domain.order.repository.OrderRepository;
import com.nbcamp.coffeeordersystem.domain.user.dto.ChargePointRequest;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PointConcurrencyTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Long userId;

    @BeforeEach
    void setUp() {
        // 이전 테스트가 남긴 데이터를 지운다 (외래 키 때문에 orders를 먼저 지운다)
        orderRepository.deleteAll();
        userRepository.deleteAll();

        // 잔액 0P인 테스트 사용자를 저장한다
        User user = userRepository.save(new User("동시충전테스트"));
        userId = user.getId();
    }

    @Test
    @DisplayName("같은 사용자에게 100P 충전 100건이 동시에 들어와도 잔액은 정확히 10,000P가 된다")
    void chargePoint_concurrently() throws InterruptedException {
        int threadCount = 100;
        long chargeAmount = 100L;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    pointService.chargePoint(new ChargePointRequest(userId, chargeAmount));
                } finally {
                    // 성공하든 실패하든 작업 하나가 끝났음을 알린다
                    latch.countDown();
                }
            });
        }

        // 100건이 모두 끝날 때까지 기다린다
        latch.await();
        executorService.shutdown();

        User user = userRepository.findById(userId).orElseThrow();
        assertThat(user.getPointBalance()).isEqualTo(10000L);
    }
}