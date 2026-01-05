package com.agroveterinaria.gestion.controller;

import com.agroveterinaria.gestion.model.Proveedor;
import com.agroveterinaria.gestion.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {
    private final ProveedorRepository proveedorRepository;

    @GetMapping
    public List<Proveedor> listar() { return proveedorRepository.findAll(); }

    @PostMapping
    public Proveedor crear(@RequestBody Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
}