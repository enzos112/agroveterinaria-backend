package com.agroveterinaria.gestion.controller;

import com.agroveterinaria.gestion.dto.request.CrearProductoDTO;
import com.agroveterinaria.gestion.dto.request.CrearVarianteDTO;
import com.agroveterinaria.gestion.dto.response.ProductoResponseDTO;
import com.agroveterinaria.gestion.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Importante: Permite que Angular consuma esta API
public class ProductoController {

    private final ProductoService productoService;

    // 1. Registrar un nuevo producto padre (Ej: "Glifosato")
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody CrearProductoDTO dto) {
        ProductoResponseDTO nuevoProducto = productoService.registrarProducto(dto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // 2. Agregar una presentación (Ej: "Botella 1L") a un producto existente
    @PostMapping("/variantes")
    public ResponseEntity<ProductoResponseDTO> agregarVariante(@Valid @RequestBody CrearVarianteDTO dto) {
        ProductoResponseDTO productoActualizado = productoService.agregarVariante(dto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.CREATED);
    }

    // 3. Listar todo el catálogo (Para la pantalla de Inventario)
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    // 4. Buscador en tiempo real (Para el Vendedor)
    // Uso: GET /api/productos/buscar?termino=glifo
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponseDTO>> buscar(@RequestParam String termino) {
        return ResponseEntity.ok(productoService.buscarPorNombre(termino));
    }
}