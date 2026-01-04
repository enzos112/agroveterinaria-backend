package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.CodigoAutorizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.time.LocalDate;

public interface CodigoAutorizacionRepository extends JpaRepository<CodigoAutorizacion, Long> {
    Optional<CodigoAutorizacion> findByFechaGeneracion(LocalDate fecha);
}