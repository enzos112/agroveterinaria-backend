package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.PagoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoVentaRepository extends JpaRepository<PagoVenta, Long> {
    // Útil para ver cómo se pagó una venta (cuánto en efectivo, cuánto en Yape)
    List<PagoVenta> findByVentaId(Long ventaId);
}