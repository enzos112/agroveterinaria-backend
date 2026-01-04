package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    // Útil para listar los productos de una venta específica en el historial
    List<DetalleVenta> findByVentaId(Long ventaId);
}