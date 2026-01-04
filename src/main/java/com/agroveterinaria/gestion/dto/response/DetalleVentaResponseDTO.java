package com.agroveterinaria.gestion.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleVentaResponseDTO {
    private String nombreItem; // Producto o Servicio
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}