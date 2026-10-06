package com.nbcamp.coffeeordersystem.domain.menu.domain.menu.controller;


import com.nbcamp.coffeeordersystem.domain.menu.dto.MenuResponse;
import com.nbcamp.coffeeordersystem.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuResponse>> getMenus() {
        return ResponseEntity.ok(menuService.findMenus());
    }
}
