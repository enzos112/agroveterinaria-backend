package com.agroveterinaria.gestion.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RegistrarVentaDTO {

    private Long clienteId; // Opcional (puede ser null si es venta rápida)

    @NotNull(message = "El monto total es obligatorio")
    @PositiveOrZero
    private BigDecimal montoTotal;

    @PositiveOrZero
    private BigDecimal descuentoGlobal;

    // Lista de productos/servicios que lleva
    @NotEmpty(message = "La venta debe tener al menos un producto o servicio")
    @Valid // Activa las validaciones dentro de la lista
    private List<DetalleVentaRequest> detalles;

    // Lista de pagos (Efectivo + Yape)
    @NotEmpty(message = "Debe registrar al menos un pago")
    @Valid
    private List<PagoVentaRequest> pagos;
}