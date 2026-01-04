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
    private BigDecimal cantidadBonificacion = BigDecimal.ZERO; // Ej: 1 unidad de regalo

    @NotNull
    @PositiveOrZero
    private BigDecimal costoUnitario; // Ej: 15 soles

    // Datos para generar el LOTE automáticamente
    private String numeroLote; // El que viene impreso en la caja

    @Future(message = "Si ingresa vencimiento, debe ser fecha futura")
    private LocalDate fechaVencimiento;
}