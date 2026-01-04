package com.agroveterinaria.gestion.dto.response;

import com.agroveterinaria.gestion.model.VarianteProducto.UnidadMedida;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VarianteResponseDTO {
    private Long id;
    private String nombreCompleto; // Ej: "Glifosato - Botella 1L"
    private BigDecimal precioVenta;
    private BigDecimal stockActual; // La suma de todos los lotes vigentes
    private UnidadMedida unidad;

    // Semáforo: Info crítica para pintar en rojo en el frontend
    private boolean stockBajo;
    private LocalDate fechaVencimientoMasProxima; // El lote más viejo
}