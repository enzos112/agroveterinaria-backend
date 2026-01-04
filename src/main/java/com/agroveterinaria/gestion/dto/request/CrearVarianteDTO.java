package com.agroveterinaria.gestion.dto.request;

import com.agroveterinaria.gestion.model.VarianteProducto.UnidadMedida;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CrearVarianteDTO {
    @NotNull(message = "El ID del producto padre es obligatorio")
    private Long productoId;

    @NotBlank(message = "El nombre de la presentación es obligatorio (Ej: Saco 50kg)")
    private String nombreVariante;

    @NotNull(message = "La unidad de medida es obligatoria")
    private UnidadMedida unidadMedida; // ENUM: UNIDAD, KG, LITRO...

    @NotNull
    @DecimalMin(value = "0.10", message = "El precio de venta debe ser mayor a 0.10")
    private BigDecimal precioVentaPublico;

    @PositiveOrZero
    private BigDecimal precioVentaMayorista;

    // Configuración para "Abrir Saco"
    private boolean sePuedeAbrir;

    @Positive(message = "El factor de conversión debe ser positivo")
    private BigDecimal factorConversion; // Ej: 50

    @PositiveOrZero
    private BigDecimal stockMinimoAlerta;
}