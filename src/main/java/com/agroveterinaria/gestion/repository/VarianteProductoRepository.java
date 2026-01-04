package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.VarianteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VarianteProductoRepository extends JpaRepository<VarianteProducto, Long> {
    // Obtener todas las presentaciones de un producto padre
    List<VarianteProducto> findByProductoId(Long productoId);

    // Para ver productos con stock bajo (según tu alerta de semáforo)
    // Nota: Esto requiere una consulta más compleja (Query) que veremos en el Servicio,
    // pero por ahora dejamos el básico.
}