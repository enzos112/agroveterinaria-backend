package com.agroveterinaria.gestion.dto.request;

import com.agroveterinaria.gestion.model.PagoVenta.MetodoPago;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoVentaRequest {
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto del pago debe ser positivo")
    private BigDecimal monto;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;
}