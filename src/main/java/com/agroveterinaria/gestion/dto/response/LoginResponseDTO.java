package com.agroveterinaria.gestion.dto.response;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String nombreUsuario;
    private String rol;
}