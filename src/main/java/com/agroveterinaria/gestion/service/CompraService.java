package com.agroveterinaria.gestion.service;

import com.agroveterinaria.gestion.dto.request.RegistrarCompraDTO;
import com.agroveterinaria.gestion.model.Compra;

public interface CompraService {
    Compra registrarCompra(RegistrarCompraDTO dto);
}