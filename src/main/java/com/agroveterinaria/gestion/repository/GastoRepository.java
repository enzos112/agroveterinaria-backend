package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByOrderByFechaDesc();
    @Query("SELECT COALESCE(SUM(g.monto), 0) FROM Gasto g WHERE g.fecha >= :inicio AND g.fecha <= :fin")
    BigDecimal sumarGastosEntre(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}