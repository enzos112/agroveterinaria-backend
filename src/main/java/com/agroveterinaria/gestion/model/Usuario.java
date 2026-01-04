package com.agroveterinaria.gestion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false, length = 50)
    private String nombreUsuario; // DNI o user corto

    @NotBlank
    @Column(nullable = false)
    private String contrasena; // Hash BCrypt

    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol; // Enum: ADMIN, VENDEDOR

    private boolean activo = true;

    public enum Rol {
        ADMIN, VENDEDOR
    }
}