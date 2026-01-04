package com.agroveterinaria.gestion.controller;

import com.agroveterinaria.gestion.dto.request.RegistrarCompraDTO;
import com.agroveterinaria.gestion.model.Compra;
import com.agroveterinaria.gestion.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<String> registrarCompra(@Valid @RequestBody RegistrarCompraDTO dto) {
        Compra nuevaCompra = compraService.registrarCompra(dto);
        return ResponseEntity.ok("Compra registrada con éxito. ID: " + nuevaCompra.getId());
    }
}