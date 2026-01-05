package com.agroveterinaria.gestion.controller;

import com.agroveterinaria.gestion.dto.request.RegistrarVentaDTO;
import com.agroveterinaria.gestion.dto.response.VentaResponseDTO;
import com.agroveterinaria.gestion.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> registrarVenta(@Valid @RequestBody RegistrarVentaDTO dto) {
        return ResponseEntity.ok(ventaService.registrarVenta(dto));
    }
}