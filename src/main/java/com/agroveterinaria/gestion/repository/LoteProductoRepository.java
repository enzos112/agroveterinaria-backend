package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.LoteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface LoteProductoRepository extends JpaRepository<LoteProducto, Long> {
    // Encontrar lotes de una variante específica ordenados por fecha de vencimiento (FIFO)
    // Útil para descontar stock del más antiguo primero
    List<LoteProducto> findByVarianteIdOrderByFechaVencimientoAsc(Long varianteId);

    // Alerta de vencimientos (Tu semáforo rojo)
    @Query("SELECT l FROM LoteProducto l WHERE l.fechaVencimiento BETWEEN :hoy AND :limite AND l.stockActual > 0")
    List<LoteProducto> findLotesPorVencer(LocalDate hoy, LocalDate limite);

    // NUEVO: Suma el stock de todos los lotes de una variante
    // COALESCE es para que si no hay lotes, devuelva 0 en vez de NULL
    @Query("SELECT COALESCE(SUM(l.stockActual), 0) FROM LoteProducto l WHERE l.variante.id = :varianteId")
    BigDecimal obtenerStockTotal(Long varianteId);

    // NUEVO: Para encontrar la fecha de vencimiento más próxima (el lote más viejo con stock)
    LoteProducto findFirstByVarianteIdAndStockActualGreaterThanOrderByFechaVencimientoAsc(Long varianteId, BigDecimal stockMayorA);
}