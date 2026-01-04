package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Para buscar productos por nombre (Buscador del vendedor)
    List<Producto> findByNombreContainingIgnoreCase(String termino);
}