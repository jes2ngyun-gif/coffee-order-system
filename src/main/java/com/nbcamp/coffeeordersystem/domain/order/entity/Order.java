package com.nbcamp.coffeeordersystem.domain.order.entity;

import com.nbcamp.coffeeordersystem.domain.menu.entity.Menu;
import com.nbcamp.coffeeordersystem.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "menu_name", nullable = false, length = 100)
    private String menuName;

    @Column(name = "payment_amount", nullable = false)
    private Long paymentAmount;

    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    public Order(User user, Menu menu, LocalDateTime orderedAt) {
        this.user = user;
        this.menu = menu;
        // 주문 시점의 메뉴 이름과 가격을 스냅샷으로 남김
        this.menuName = menu.getName();
        this.paymentAmount = menu.getPrice();
        this.orderedAt = orderedAt;
    }
}
