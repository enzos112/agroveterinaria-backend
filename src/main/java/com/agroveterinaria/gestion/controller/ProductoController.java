package com.agroveterinaria.gestion.controller;

import com.agroveterinaria.gestion.dto.request.CrearProductoDTO;
import com.agroveterinaria.gestion.dto.request.CrearVarianteDTO;
import com.agroveterinaria.gestion.dto.response.ProductoResponseDTO;
import com.agroveterinaria.gestion.model.Producto;
import com.agroveterinaria.gestion.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponseDTO>> buscar(@RequestParam String termino) {
        return ResponseEntity.ok(productoService.buscarPorNombre(termino));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody CrearProductoDTO dto) {
        return ResponseEntity.ok(productoService.registrarProducto(dto));
    }

    @PostMapping("/variantes")
    public ResponseEntity<ProductoResponseDTO> agregarVariante(@Valid @RequestBody CrearVarianteDTO dto) {
        return ResponseEntity.ok(productoService.agregarVariante(dto));
    }

    @PostMapping("/abrir/{id}")
    public ResponseEntity<Producto> abrirUnidad(@PathVariable Long id) {
        Producto productoActualizado = productoService.abrirUnidad(id);
        return ResponseEntity.ok(productoActualizado);
    }
}