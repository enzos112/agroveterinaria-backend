package com.agroveterinaria.gestion.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DetalleCompraRequest {
    @NotNull
    private Long varianteId;

    @NotNull
    @Positive(message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidadComprada; // Ej: 12 unidades

    @PositiveOrZero
    private BigDecimal cantidadBonificacion = BigDecimal.ZERO;

    @NotNull
    @PositiveOrZero
    private BigDecimal costoUnitario;

    private String numeroLote;

    @Future(message = "Si ingresa vencimiento, debe ser fecha futura")
    private LocalDate fechaVencimiento;
}