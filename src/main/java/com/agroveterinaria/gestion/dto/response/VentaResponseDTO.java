package com.agroveterinaria.gestion.dto.response;

import com.agroveterinaria.gestion.model.Venta.EstadoVenta;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private String nombreVendedor;
    private String nombreCliente; // "Cliente Anónimo" si es null
    private BigDecimal total;
    private BigDecimal descuentoAplicado;
    private EstadoVenta estado;

    private List<DetalleVentaResponseDTO> productos;
}