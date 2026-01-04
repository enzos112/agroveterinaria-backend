package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "compras")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(length = 50)
    private String numeroDocumento; // Factura

    @NotNull
    private LocalDate fechaCompra;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costoTotalProductos;

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal costoFlete = BigDecimal.ZERO;

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal montoPagado = BigDecimal.ZERO; // Para controlar deuda

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPagoCompra estadoPago;

    public enum EstadoPagoCompra {
        PAGADO, DEUDA, PARCIAL
    }
}