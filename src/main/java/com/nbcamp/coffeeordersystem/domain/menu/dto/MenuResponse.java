package com.nbcamp.coffeeordersystem.domain.menu.dto;

import com.nbcamp.coffeeordersystem.domain.menu.entity.Menu;

public record MenuResponse(Long menuId, String name, Long price) {

    public static MenuResponse from(Menu menu) {

        return new MenuResponse(menu.getId(), menu.getName(), menu.getPrice());
    }
}
