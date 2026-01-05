package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "variantes_producto")
public class VarianteProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombreVariante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadMedida unidadMedida;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVentaPublico;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioVentaMayorista;

    private boolean sePuedeAbrir = false;

    @Positive
    @Column(precision = 10, scale = 2)
    private BigDecimal factorConversion = BigDecimal.ONE;

    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal stockMinimoAlerta = new BigDecimal("5.00");

    public enum UnidadMedida {
        UNIDAD, KG, LITRO, ML, METRO, CAJA
    }
}