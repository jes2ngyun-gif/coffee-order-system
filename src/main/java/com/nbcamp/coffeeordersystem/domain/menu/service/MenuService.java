package com.nbcamp.coffeeordersystem.domain.menu.service;

import com.nbcamp.coffeeordersystem.domain.menu.dto.MenuResponse;
import com.nbcamp.coffeeordersystem.domain.menu.entity.Menu;
import com.nbcamp.coffeeordersystem.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public List<MenuResponse> findMenus() {

        List<Menu> menus = menuRepository.findAllByOrderByIdAsc();

        List<MenuResponse> responses = new ArrayList<>();

        for (Menu menu : menus) {
            responses.add(MenuResponse.from(menu));
        }

        return responses;
    }
}
