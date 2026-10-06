package com.nbcamp.coffeeordersystem.domain.user.service;

import com.nbcamp.coffeeordersystem.common.exception.BusinessException;
import com.nbcamp.coffeeordersystem.common.exception.ErrorCode;
import com.nbcamp.coffeeordersystem.domain.user.dto.ChargePointRequest;
import com.nbcamp.coffeeordersystem.domain.user.dto.ChargePointResponse;
import com.nbcamp.coffeeordersystem.domain.user.entity.User;
import com.nbcamp.coffeeordersystem.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;

    @Transactional
    public ChargePointResponse chargePoint(ChargePointRequest request) {

        // 락을 잡기 전에 잘못된 요청을 먼저 거절
        if (request.userId() == null || request.amount() ==null) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        if (request.amount() <= 0) {
            throw new BusinessException(ErrorCode.INVALID_CHARGE_AMOUNT);
        }

        // 사용자 조회와 동시에 해당 USERS Row에 비관적 락 획득!
        User user = userRepository.findByIdForUpdate(request.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 실제 잔액 변경 규칙은 User 엔티티가 담당
        user. chargePoint(request.amount());

        // save() 없이 더티체킹으로 UPDATE되고, 커밋 시점에 락이 풀림
        return ChargePointResponse.of(user, request.amount());
    }
}
