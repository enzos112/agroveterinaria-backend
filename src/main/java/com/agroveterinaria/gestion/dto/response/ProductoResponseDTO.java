package com.agroveterinaria.gestion.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String categoria; // Solo el nombre de la categoría, no el objeto entero
    private boolean tieneVencimiento;

    // Lista de sus variantes (Ej: "Botella 1L", "Saco 50kg")
    private List<VarianteResponseDTO> presentaciones;
}