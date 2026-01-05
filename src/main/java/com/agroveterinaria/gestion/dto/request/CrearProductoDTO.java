package com.agroveterinaria.gestion.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CrearProductoDTO {
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 3, max = 100)
    private String nombre;

    private String descripcion;

    @NotNull(message = "Debe indicar si tiene vencimiento")
    private Boolean tieneVencimiento;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;
}