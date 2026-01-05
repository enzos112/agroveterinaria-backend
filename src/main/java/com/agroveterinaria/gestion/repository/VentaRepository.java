package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT COALESCE(SUM(v.totalVenta), 0) FROM Venta v WHERE v.fechaVenta >= :inicio AND v.fechaVenta <= :fin")
    BigDecimal sumarVentasEntre(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query("SELECT COUNT(v) FROM Venta v WHERE v.fechaVenta >= :inicio AND v.fechaVenta <= :fin")
    long countByFechaBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}