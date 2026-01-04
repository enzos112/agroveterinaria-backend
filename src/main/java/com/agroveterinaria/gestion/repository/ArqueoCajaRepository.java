package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.ArqueoCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ArqueoCajaRepository extends JpaRepository<ArqueoCaja, Long> {
    // Buscar si hay una caja abierta actualmente
    Optional<ArqueoCaja> findByEstado(ArqueoCaja.EstadoCaja estado);
}