package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "arqueos_caja")
public class ArqueoCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime fechaApertura;

    private LocalDateTime fechaCierre;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldoInicialEfectivo;

    // Campos calculados al cierre (Snapshot)
    @Column(precision = 10, scale = 2)
    private BigDecimal saldoEsperadoEfectivo;

    @Column(precision = 10, scale = 2)
    private BigDecimal saldoEsperadoDigital;

    // Lo que el usuario cuenta
    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal saldoRealEfectivo;

    @Column(precision = 10, scale = 2)
    private BigDecimal diferencia; // Real - Esperado

    @Column(columnDefinition = "TEXT")
    private String justificacionDiferencia; // Obligatorio si diferencia != 0

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCaja estado;

    public enum EstadoCaja {
        ABIERTA, CERRADA
    }
}