package com.agroveterinaria.gestion.dto.response;

import com.agroveterinaria.gestion.model.VarianteProducto.UnidadMedida;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VarianteResponseDTO {
    private Long id;
    private String nombreCompleto;
    private BigDecimal precioVenta;
    private BigDecimal stockActual;
    private UnidadMedida unidad;

    private boolean stockBajo;
    private LocalDate fechaVencimientoMasProxima;
}