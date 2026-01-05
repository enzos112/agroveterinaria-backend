package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.PagoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoProveedorRepository extends JpaRepository<PagoProveedor, Long> {
    List<PagoProveedor> findByCompraId(Long compraId);
}