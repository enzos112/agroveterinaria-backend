package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
    // Útil para ver qué productos se compraron en una factura específica
    List<DetalleCompra> findByCompraId(Long compraId);
}