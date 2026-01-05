package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "gastos")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double monto;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private CategoriaGasto categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }

    public enum CategoriaGasto {
        INSUMOS,
        TRANSPORTE,
        OTROS
    }
}