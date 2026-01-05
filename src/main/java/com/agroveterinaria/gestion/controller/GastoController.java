package com.agroveterinaria.gestion.controller;

import com.agroveterinaria.gestion.model.Gasto;
import com.agroveterinaria.gestion.service.GastoService;
import com.agroveterinaria.gestion.service.impl.GastoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
@RequiredArgsConstructor
public class GastoController {

    private final GastoServiceImpl gastoService;

    @GetMapping
    public List<Gasto> listar() {
        return gastoService.listarGastos();
    }

    @PostMapping
    public ResponseEntity<Gasto> registrar(
            @RequestBody Gasto gasto,
            @RequestParam(required = false) Long usuarioId
    ) {
        return ResponseEntity.ok(gastoService.registrarGasto(gasto, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gastoService.eliminarGasto(id);
        return ResponseEntity.noContent().build();
    }
}