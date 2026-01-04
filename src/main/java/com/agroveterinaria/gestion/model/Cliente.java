package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    // Validación estricta Perú: Empieza con 9, tiene 9 dígitos, no 4 repetidos seguidos
    @Pattern(regexp = "^(?!.*(\\d)\\1{3})9\\d{8}$", message = "Celular inválido")
    @Column(length = 9)
    private String telefono;

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal limiteCredito = BigDecimal.ZERO;

    @PositiveOrZero
    @Column(precision = 10, scale = 2)
    private BigDecimal deudaActual = BigDecimal.ZERO;
}