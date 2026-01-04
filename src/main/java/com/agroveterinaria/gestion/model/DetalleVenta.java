package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "detalles_venta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    // Puede ser Producto O Servicio (uno será null)
    @ManyToOne
    @JoinColumn(name = "variante_id")
    private VarianteProducto variante;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoItem tipoItem; // PRODUCTO o SERVICIO

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitarioVenta;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    private TipoPrecio tipoPrecioAplicado; // PUBLICO, MAYORISTA, NEGOCIADO

    public enum TipoItem { PRODUCTO, SERVICIO }
    public enum TipoPrecio { PUBLICO, MAYORISTA, NEGOCIADO }
}