package com.agroveterinaria.gestion.service;

import com.agroveterinaria.gestion.dto.request.CrearProductoDTO;
import com.agroveterinaria.gestion.dto.request.CrearVarianteDTO;
import com.agroveterinaria.gestion.dto.response.ProductoResponseDTO;
import java.util.List;

public interface ProductoService {

    // 1. Crear el producto padre (Ej: "Glifosato")
    ProductoResponseDTO registrarProducto(CrearProductoDTO dto);

    // 2. Agregarle tamaños/presentaciones (Ej: "Botella 1L")
    ProductoResponseDTO agregarVariante(CrearVarianteDTO dto);

    // 3. Listar todo para el catálogo
    List<ProductoResponseDTO> listarTodos();

    // 4. Buscar (para el buscador del vendedor)
    List<ProductoResponseDTO> buscarPorNombre(String termino);
}