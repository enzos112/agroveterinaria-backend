package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "lotes_producto")
public class LoteProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "variante_id", nullable = false)
    private VarianteProducto variante;

    @Column(length = 50)
    private String numeroLote; // Opcional

    private LocalDate fechaVencimiento;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaIngreso;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal stockActual;
}