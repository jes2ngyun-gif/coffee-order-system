package com.nbcamp.coffeeordersystem.domain.order.service;

import com.nbcamp.coffeeordersystem.common.exception.BusinessException;
import com.nbcamp.coffeeordersystem.common.exception.ErrorCode;
import com.nbcamp.coffeeordersystem.domain.menu.entity.Menu;
import com.nbcamp.coffeeordersystem.domain.menu.repository.MenuRepository;
import com.nbcamp.coffeeordersystem.domain.order.dto.CreateOrderRequest;
import com.nbcamp.coffeeordersystem.domain.order.dto.CreateOrderResponse;
import com.nbcamp.coffeeordersystem.domain.order.entity.Order;
import com.nbcamp.coffeeordersystem.domain.order.repository.OrderRepository;
import com.nbcamp.coffeeordersystem.domain.user.entity.User;
import com.nbcamp.coffeeordersystem.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        // 락을 잡기 전에 잘못된 요청을 먼저 거절
        if (request.userId() == null || request.menuId() == null) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        // 1. 메뉴 조회: 락을 쥐는 시간을 줄이기 위해 락 획득 전에 수행함
        Menu menu = menuRepository.findById(request.menuId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND));

        // 2. 사용자 조회와 동시에 해당 USERS Row에 비관적 락을 획득!
        User user = userRepository.findByIdForUpdate(request.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 3. 잔액 검증과 차감은 User 엔티티가 담당한다
        user.usePoint(menu.getPrice());

        // 4. 주문 저장: 메뉴 이름과 가격이 Snapshot으로 남음
        Order order = new Order(user, menu, LocalDateTime.now());
        orderRepository.save(order);

        // 메서드가 끝나면 포인트 차감과 주문 저장이 함께 커밋되고 락이 풀린다
        return CreateOrderResponse.of(order, user);
    }
}