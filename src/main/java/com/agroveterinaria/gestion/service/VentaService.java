package com.agroveterinaria.gestion.service;

import com.agroveterinaria.gestion.dto.request.RegistrarVentaDTO;
import com.agroveterinaria.gestion.dto.response.VentaResponseDTO;

public interface VentaService {
    VentaResponseDTO registrarVenta(RegistrarVentaDTO dto);
}