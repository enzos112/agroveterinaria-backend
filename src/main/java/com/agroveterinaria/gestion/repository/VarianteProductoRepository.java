package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.VarianteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VarianteProductoRepository extends JpaRepository<VarianteProducto, Long> {
    List<VarianteProducto> findByProductoId(Long productoId);

}