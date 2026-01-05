package com.agroveterinaria.gestion.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RegistrarVentaDTO {

    private Long clienteId;

    @NotNull(message = "El monto total es obligatorio")
    @PositiveOrZero
    private BigDecimal montoTotal;

    @PositiveOrZero
    private BigDecimal descuentoGlobal;

    @NotEmpty(message = "La venta debe tener al menos un producto o servicio")
    @Valid
    private List<DetalleVentaRequest> detalles;

    @NotEmpty(message = "Debe registrar al menos un pago")
    @Valid
    private List<PagoVentaRequest> pagos;
}