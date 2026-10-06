package com.nbcamp.coffeeordersystem.domain.user.controller;

import com.nbcamp.coffeeordersystem.domain.user.dto.ChargePointRequest;
import com.nbcamp.coffeeordersystem.domain.user.dto.ChargePointResponse;
import com.nbcamp.coffeeordersystem.domain.user.service.PointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @PostMapping("/charge")
    public ResponseEntity<ChargePointResponse> chargePoint(
            @Valid @RequestBody ChargePointRequest request
    ) {
        return ResponseEntity.ok(pointService.chargePoint(request));
    }
}
