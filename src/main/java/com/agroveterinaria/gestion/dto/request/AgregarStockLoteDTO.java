package com.agroveterinaria.gestion.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AgregarStockLoteDTO {
    @NotNull(message = "Debe seleccionar la variante del producto")
    private Long varianteId;

    @Pattern(regexp = "^[A-Z0-9-]+$", message = "El lote solo acepta letras mayúsculas, números y guiones")
    private String numeroLote;

    @Future(message = "La fecha de vencimiento debe ser futura")
    private LocalDate fechaVencimiento;

    @NotNull
    @Positive(message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidad;
}