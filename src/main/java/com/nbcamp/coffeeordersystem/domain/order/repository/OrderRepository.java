package com.nbcamp.coffeeordersystem.domain.order.repository;

import com.nbcamp.coffeeordersystem.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
