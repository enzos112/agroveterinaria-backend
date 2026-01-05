package com.agroveterinaria.gestion.service;

import com.agroveterinaria.gestion.dto.request.CrearProductoDTO;
import com.agroveterinaria.gestion.dto.request.CrearVarianteDTO;
import com.agroveterinaria.gestion.dto.response.ProductoResponseDTO;
import com.agroveterinaria.gestion.model.Producto; // Import necesario

import java.util.List;

public interface ProductoService {

    ProductoResponseDTO registrarProducto(CrearProductoDTO dto);

    ProductoResponseDTO agregarVariante(CrearVarianteDTO dto);

    List<ProductoResponseDTO> listarTodos();

    List<ProductoResponseDTO> buscarPorNombre(String termino);

    Producto abrirUnidad(Long idProducto);
}