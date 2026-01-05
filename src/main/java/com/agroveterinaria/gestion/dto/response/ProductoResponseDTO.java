package com.agroveterinaria.gestion.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private boolean tieneVencimiento;

    private List<VarianteResponseDTO> presentaciones;
}