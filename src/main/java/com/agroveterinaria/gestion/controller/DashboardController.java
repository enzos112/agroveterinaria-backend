package com.agroveterinaria.gestion.controller;

import com.agroveterinaria.gestion.dto.response.DashboardDTO;
import com.agroveterinaria.gestion.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/resumen")
    public ResponseEntity<DashboardDTO> obtenerResumen() {
        return ResponseEntity.ok(dashboardService.obtenerResumenHoy());
    }
}