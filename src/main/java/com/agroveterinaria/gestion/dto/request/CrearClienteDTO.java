package com.agroveterinaria.gestion.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CrearClienteDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombreCompleto;

    @Pattern(regexp = "^(?!.*(\\d)\\1{3})9\\d{8}$",
            message = "Número inválido: Debe empezar con 9, tener 9 dígitos y no tener 4 números iguales seguidos")
    private String telefono;

    @PositiveOrZero(message = "El límite de crédito no puede ser negativo")
    @Max(value = 5000, message = "Por seguridad, el crédito inicial no puede superar los S/ 5,000")
    private BigDecimal limiteCredito;
}