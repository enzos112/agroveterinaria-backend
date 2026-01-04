package com.agroveterinaria.gestion.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class RegistrarCompraDTO {
    @NotNull(message = "Debe seleccionar un proveedor")
    private Long proveedorId;

    private String numeroDocumento; // Factura F001-234

    @NotNull
    @PastOrPresent(message = "La fecha de compra no puede ser futura")
    private LocalDate fechaCompra;

    @NotNull
    @PositiveOrZero
    private BigDecimal costoTotalProductos;

    @PositiveOrZero
    private BigDecimal costoFlete = BigDecimal.ZERO;

    // Lista de productos que entran
    @NotEmpty(message = "La compra debe tener al menos un producto")
    @Valid
    private List<DetalleCompraRequest> detalles;

    // Pago inicial (Puede ser parcial, total o 0 si es crédito)
    @Valid
    private PagoCompraRequest pagoInicial;
}