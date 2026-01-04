package com.agroveterinaria.gestion.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CierreCajaDTO {
    @NotNull
    @PositiveOrZero(message = "El monto contado no puede ser negativo")
    private BigDecimal efectivoContadoPorUsuario;

    private String justificacion; // Opcional al inicio, obligatorio si hay error
}