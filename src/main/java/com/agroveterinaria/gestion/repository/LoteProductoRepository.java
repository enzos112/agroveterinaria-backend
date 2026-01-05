package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.LoteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface LoteProductoRepository extends JpaRepository<LoteProducto, Long> {

    List<LoteProducto> findByVarianteIdOrderByFechaVencimientoAsc(Long varianteId);

    @Query("SELECT l FROM LoteProducto l WHERE l.fechaVencimiento BETWEEN :hoy AND :limite AND l.stockActual > 0")
    List<LoteProducto> findLotesPorVencer(LocalDate hoy, LocalDate limite);

    @Query("SELECT COALESCE(SUM(l.stockActual), 0) FROM LoteProducto l WHERE l.variante.id = :varianteId")
    BigDecimal obtenerStockTotal(Long varianteId);

    LoteProducto findFirstByVarianteIdAndStockActualGreaterThanOrderByFechaVencimientoAsc(Long varianteId, BigDecimal stockMayorA);
}