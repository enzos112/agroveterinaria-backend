package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    // Para cargar el selector (dropdown) de servicios en la venta
    // Solo mostramos los servicios activos (por si dejas de ofrecer alguno)
    List<Servicio> findByActivoTrue();
}