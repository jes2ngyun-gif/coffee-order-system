package com.nbcamp.coffeeordersystem.domain.user.entity;

import com.nbcamp.coffeeordersystem.common.entity.BaseTimeEntity;
import com.nbcamp.coffeeordersystem.common.exception.BusinessException;
import com.nbcamp.coffeeordersystem.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "point_balance", nullable = false)
    private Long pointBalance;

    public User(String name) {
        this.name = name;
        this.pointBalance = 0L;        // 새 사용자의 포인트는 항상 0에서 시작
    }

    // 포인트 충전
    public void chargePoint(Long amount) {
        if (amount == null || amount <= 0) {
            throw new BusinessException(ErrorCode.INVALID_CHARGE_AMOUNT);
        }
        this.pointBalance = this.pointBalance + amount;
    }

    // 포인트 사용
    public void usePoint(Long amount) {
        if (amount == null || amount <= 0) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        if (this.pointBalance < amount) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_POINT);
        }

        this.pointBalance = this.pointBalance - amount;
    }
}
