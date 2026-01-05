package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.PagoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoVentaRepository extends JpaRepository<PagoVenta, Long> {
    List<PagoVenta> findByVentaId(Long ventaId);
}