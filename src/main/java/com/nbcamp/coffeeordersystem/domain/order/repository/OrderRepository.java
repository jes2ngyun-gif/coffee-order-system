package com.nbcamp.coffeeordersystem.domain.order.repository;

import com.nbcamp.coffeeordersystem.domain.order.dto.PopularMenuResult;
import com.nbcamp.coffeeordersystem.domain.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 기준 시각 이후의 주문을 메뉴별로 집계해 주문 횟수가 많은 순으로 조회한다
    @Query("select new com.nbcamp.coffeeordersystem.domain.order.dto.PopularMenuResult(m.id, m.name, count(o)) " +
            "from Order o " +
            "join o.menu m " +
            "where o.orderedAt >= :since " +
            "group by m.id, m.name " +
            "order by count(o) desc, m.id asc")
    List<PopularMenuResult> findPopularMenus(@Param("since") LocalDateTime since, Pageable pageable);
}