package com.agroveterinaria.gestion.dto.response;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token; // El JWT largo
    private String nombreUsuario;
    private String rol; // "ADMIN" o "VENDEDOR"
}