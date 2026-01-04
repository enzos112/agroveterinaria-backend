package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Para reportes de ventas entre fechas
    List<Venta> findByFechaVentaBetween(LocalDateTime inicio, LocalDateTime fin);

    // Para ver ventas de un usuario específico (cierre ciego)
    List<Venta> findByUsuarioIdAndFechaVentaBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fin);
}