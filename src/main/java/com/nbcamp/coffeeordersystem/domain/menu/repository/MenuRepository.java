package com.nbcamp.coffeeordersystem.domain.menu.repository;

import com.nbcamp.coffeeordersystem.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    // 정렬 기준이 없으면 조회 순서가 보장되지 않음 -> ID 오름차순으로 고정
    List<Menu> findAllByOrderByIdAsc();
}
