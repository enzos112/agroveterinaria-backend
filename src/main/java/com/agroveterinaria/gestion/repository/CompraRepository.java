package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByEstadoPagoIn(List<Compra.EstadoPagoCompra> estados);
}