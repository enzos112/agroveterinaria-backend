package com.agroveterinaria.gestion.dto.request;

import com.agroveterinaria.gestion.model.DetalleVenta.TipoItem;
import com.agroveterinaria.gestion.model.DetalleVenta.TipoPrecio;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleVentaRequest {
    // Uno de los dos debe tener valor
    private Long varianteId;
    private Long servicioId;

    @NotNull(message = "El tipo de item es obligatorio (PRODUCTO o SERVICIO)")
    private TipoItem tipoItem;

    @NotNull
    @Positive(message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidad;

    @NotNull
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private BigDecimal precioUnitarioPactado;

    @NotNull
    private TipoPrecio tipoPrecio;
}