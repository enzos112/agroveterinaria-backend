package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombreEmpresa;

    @Column(length = 100)
    private String contacto; // Nombre de la persona

    @Column(length = 20)
    private String telefono;
}