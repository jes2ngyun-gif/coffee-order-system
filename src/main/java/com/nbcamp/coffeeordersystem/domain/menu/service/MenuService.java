package com.nbcamp.coffeeordersystem.domain.menu.service;

import com.nbcamp.coffeeordersystem.domain.menu.dto.MenuResponse;
import com.nbcamp.coffeeordersystem.domain.menu.dto.PopularMenuResponse;
import com.nbcamp.coffeeordersystem.domain.menu.entity.Menu;
import com.nbcamp.coffeeordersystem.domain.menu.repository.MenuRepository;
import com.nbcamp.coffeeordersystem.domain.order.dto.PopularMenuResult;
import com.nbcamp.coffeeordersystem.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    private static final int POPULAR_MENU_DAYS = 7;
    private static final int POPULAR_MENU_SIZE = 3;

    @Transactional(readOnly = true)
    public List<MenuResponse> findMenus() {

        List<Menu> menus = menuRepository.findAllByOrderByIdAsc();

        List<MenuResponse> responses = new ArrayList<>();

        for (Menu menu : menus) {
            responses.add(MenuResponse.from(menu));
        }

        return responses;
    }

    @Transactional(readOnly = true)
    public List<PopularMenuResponse> findPopularMenus() {

        // 최근 7일: 조회 시각 기준 정확히 7일(168시간) 전부터 현재까지
        LocalDateTime since = LocalDateTime.now().minusDays(POPULAR_MENU_DAYS);

        // 주문 횟수가 많은 순으로 상위 3개만 조회
        List<PopularMenuResult> results =
                orderRepository.findPopularMenus(since, PageRequest.of(0, POPULAR_MENU_SIZE));

        // 조회된 순서대로 1위부터 순위를 붙인다
        List<PopularMenuResponse> responses = new ArrayList<>();
        int rank = 1;
        for (PopularMenuResult result : results) {
            responses.add(PopularMenuResponse.of(rank, result));
            rank++;
        }
        return responses;
    }
}
