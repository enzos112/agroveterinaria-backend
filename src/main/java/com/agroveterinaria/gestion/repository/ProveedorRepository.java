package com.agroveterinaria.gestion.repository;

import com.agroveterinaria.gestion.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
}