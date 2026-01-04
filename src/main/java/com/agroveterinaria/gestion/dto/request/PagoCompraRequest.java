package com.agroveterinaria.gestion.dto.request;

import com.agroveterinaria.gestion.model.PagoProveedor.MetodoPagoProveedor;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoCompraRequest {
    @NotNull
    @PositiveOrZero
    private BigDecimal monto;

    @NotNull
    private MetodoPagoProveedor metodoPago; // EFECTIVO_CAJA o BANCO
}