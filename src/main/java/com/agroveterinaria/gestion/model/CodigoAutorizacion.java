package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "codigo_autorizacion_diario")
public class CodigoAutorizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 4)
    private String codigo;

    @Column(unique = true, nullable = false)
    private LocalDate fechaGeneracion;
}